package com.tingcore.cdc.payments.pricing.repository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.ElasticSearchException;
import com.tingcore.cdc.payments.pricing.model.ConnectorPriceProfile;
import com.tingcore.emp.pricing.profile.Source;
import com.tingcore.emp.pricing.profile.response.PriceProfileAssociationResponse;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tingcore.cdc.constant.BeanQualifierConstants.VANILLA_OBJECT_MAPPER;
import static com.tingcore.cdc.constant.SpringProfilesConstant.PRICE_PROFILES_FROM_REST;
import static org.elasticsearch.index.query.QueryBuilders.*;

@Repository
@Profile("!" + PRICE_PROFILES_FROM_REST)
public class ElasticSearchPricingRepository implements PricingRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchPricingRepository.class);

    // indexes
    private static final String PROFILES_INDEX = "price-profiles";
    private static final String ASSOCIATIONS_INDEX = "price-profile-associations";

    // fields
    private static final String CONNECTOR_ID = "connectorId";
    private static final String VALID_FROM = "validFrom";
    private static final String SOURCE = "source";
    private static final String CHARGING_KEY_GROUP = "userGroupId";

    // aggregations
    private static final String ASSOCIATIONS_BY_CONNECTOR = "associations_by_connector";
    private static final String ASSOCIATIONS_BY_CONNECTOR_SOURCE = "associations_by_connector_source";
    private static final String CONNECTOR_CURRENT_ASSOCIATION = "connector_current_associations";


    private final RestHighLevelClient esClient;
    private final ObjectMapper objectMapper;

    public ElasticSearchPricingRepository(final RestHighLevelClient esClient, @Qualifier(VANILLA_OBJECT_MAPPER) final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.esClient = esClient;
    }

    @Override
    public List<ConnectorPriceProfile> getCurrentProfileForConnectors(final List<Long> connectorIds) {
        final List<PriceProfileAssociationResponse> associations = getCurrentPriceProfileAssociations(connectorIds);
        final Map<String, List<PriceProfileResponse>> priceProfiles = getPriceProfiles(associations)
                .stream()
                .collect(Collectors.groupingBy(PriceProfileResponse::getId));

        final Map<Long, List<ConnectorPriceProfile>> connectorPriceProfiles = associations.stream()
                .filter(a -> priceProfiles.containsKey(a.getProfileId()))
                .filter(a -> priceProfiles.get(a.getProfileId()).size() > 0)
                .map(a -> new ConnectorPriceProfile(a.getConnectorId(), priceProfiles.get(a.getProfileId()).get(0)))
                .collect(Collectors.groupingBy(ConnectorPriceProfile::getConnectorId));

        // add empty ConnectorPriceProfiles for those connectors we couldn't find profiles for
        return connectorIds.stream()
                .map(connectorId -> {
                    if (connectorPriceProfiles.containsKey(connectorId)) {
                        return connectorPriceProfiles.get(connectorId).get(0);
                    }
                    return new ConnectorPriceProfile(connectorId, null);
                })
                .collect(Collectors.toList());

    }

    private List<PriceProfileAssociationResponse> getCurrentPriceProfileAssociations(final List<Long> connectorIds) {
        final QueryBuilder query = boolQuery()
                .must(termsQuery(CONNECTOR_ID, connectorIds))
                .must(termQuery(SOURCE, Source.ALL.name()))
                .mustNot(existsQuery(CHARGING_KEY_GROUP))
                .must(rangeQuery(VALID_FROM).lte("now"));

        final AggregationBuilder aggregation = AggregationBuilders
                .terms(ASSOCIATIONS_BY_CONNECTOR)
                .field(CONNECTOR_ID)
                .subAggregation(
                        AggregationBuilders
                                .terms(ASSOCIATIONS_BY_CONNECTOR_SOURCE)
                                .field(SOURCE)
                                .subAggregation(
                                        AggregationBuilders
                                                .topHits(CONNECTOR_CURRENT_ASSOCIATION)
                                                .sort(SortBuilders.fieldSort(VALID_FROM).order(SortOrder.DESC))
                                                .size(1)
                                                .fetchSource(true)
                                )
                );

        final SearchSourceBuilder source = new SearchSourceBuilder()
                .query(query)
                .aggregation(aggregation)
                .size(0);
        final SearchRequest req = new SearchRequest(ASSOCIATIONS_INDEX).source(source);
        try {
            final Terms result = esClient
                    .search(req)
                    .getAggregations()
                    .get(ASSOCIATIONS_BY_CONNECTOR);

            return result
                    .getBuckets().stream().map(Terms.Bucket::getAggregations)
                    .map(a -> a.get(ASSOCIATIONS_BY_CONNECTOR_SOURCE)).map(Terms.class::cast).flatMap(a -> a.getBuckets().stream()).map(Terms.Bucket::getAggregations)
                    .map(a -> a.get(CONNECTOR_CURRENT_ASSOCIATION)).map(TopHits.class::cast).map(TopHits::getHits).flatMap(hits -> Arrays.stream(hits.getHits()))
                    .map(SearchHit::getSourceAsString)
                    .map(this::mapAssociation)
                    .filter(Optional::isPresent).map(Optional::get).map(ESAssociation::getAssociation)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ElasticSearchException("Elasticsearch request terminated with an error", e);
        }
    }

    private List<PriceProfileResponse> getPriceProfiles(final List<PriceProfileAssociationResponse> associations) {
        final List<String> priceProfilesIds = associations.stream().map(PriceProfileAssociationResponse::getProfileId).distinct().collect(Collectors.toList());
        final QueryBuilder query = boolQuery().must(termsQuery("id", priceProfilesIds));
        final SearchSourceBuilder source = new SearchSourceBuilder().query(query);
        final SearchRequest req = new SearchRequest(PROFILES_INDEX).source(source);

        try {
            return Arrays.stream(esClient.search(req).getHits().getHits())
                    .map(SearchHit::getSourceAsString)
                    .map(this::mapProfile)
                    .filter(Optional::isPresent).map(Optional::get).map(ESProfile::getProfile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ElasticSearchException("Elasticsearch request terminated with an error", e);
        }
    }

    private Optional<ESAssociation> mapAssociation(final String json) {
        try {
            return Optional.of(objectMapper.readValue(json, ESAssociation.class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LOGGER.warn("Cannot deserialize {} to an instance of ESAssociation", json);
            return Optional.empty();
        }
    }

    private Optional<ESProfile> mapProfile(final String json) {
        try {
            return Optional.of(objectMapper.readValue(json, ESProfile.class));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LOGGER.warn("Cannot deserialize {} to an instance of PriceProfileResponse", json);
            return Optional.empty();
        }
    }

    private static class ESAssociation {
        private final PriceProfileAssociationResponse association;

        @JsonCreator
        public ESAssociation(
                @JsonProperty("partitionKey") final String id,
                @JsonProperty("connectorId") long connectorId,
                @JsonProperty("validFrom") final Instant validFrom,
                @JsonProperty("organizationId") final long organizationId,
                @JsonProperty("profileId") final String profileId,
                @JsonProperty("createdAt") final Instant createdAt,
                @JsonProperty("validTo") final Instant validTo) {
            association = new PriceProfileAssociationResponse(id, connectorId, validFrom, organizationId, profileId, createdAt, validTo);
        }

        public PriceProfileAssociationResponse getAssociation() {
            return association;
        }
    }

    private static class ESProfile {
        private final PriceProfileResponse profile;

        @JsonCreator
        public ESProfile(
                @JsonProperty("id") final String id,
                @JsonProperty("organizationId") final long organizationId,
                @JsonProperty("name") final String name,
                @JsonProperty("content") final JsonNode content,
                @JsonProperty("currency") final String currency,
                @JsonProperty("createdAt") final Instant createdAt,
                @JsonProperty("source") final Source source,
                @JsonProperty("description") final String description,
                @JsonProperty("userGroupId") final Long userGroupId) {
            profile = new PriceProfileResponse(id, organizationId, name, content.toString(), currency, createdAt, source, description, userGroupId);
        }

        public PriceProfileResponse getProfile() {
            return profile;
        }
    }

}
