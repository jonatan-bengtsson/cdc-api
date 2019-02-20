package com.tingcore.cdc.payments.pricing.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.payments.pricing.model.ConnectorPriceProfile;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.emp.pricing.client.api.v1.PricingProfileRestApi;
import com.tingcore.emp.pricing.profile.Source;
import com.tingcore.emp.pricing.profile.response.PriceProfileAssociationResponse;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import com.tingcore.emp.pricing.rule.PriceRules;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.tingcore.cdc.constant.SpringProfilesConstant.PRICE_PROFILES_FROM_REST;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Repository
@Profile(PRICE_PROFILES_FROM_REST)
public class RestPricingRepository extends AbstractApiRepository implements PricingRepository {
    private static final Logger LOG = LoggerFactory.getLogger(RestPricingRepository.class);

    private final Integer defaultTimeout;
    @ApiModelProperty(dataType = "PriceRules")
    private final PricingProfileRestApi priceProfileApi;

    public RestPricingRepository(
            final ObjectMapper objectMapper,
            final PricingProfileRestApi priceProfileApi,
            @Value("${app.payments-pricing.default-timeout:20}") final Integer defaultTimeout) {
        super(objectMapper);
        this.defaultTimeout = defaultTimeout;
        this.priceProfileApi = priceProfileApi;
    }

    /**
     * ConnectorPriceProfile will have:
     * profile = Optional.empty() when no profile has been found for a connectorId
     * profile = profile when the profile has been found
     *
     * When unexpected errors happen no ConnectorPriceProfile will be generated for the related connectorId
     * @param connectorIds
     * @return List<ConnectorPriceProfile>
     */
    @Override
    public List<ConnectorPriceProfile> getCurrentProfileForConnectors(final List<Long> connectorIds) {
        return connectorIds
                .parallelStream()
                .map(this::getConnectorAssociations)
                .filter(Optional::isPresent).map(Optional::get)
                .map(this::retrieveConnectorPriceIfAny)
                .filter(prices -> !prices.isEmpty())
                .flatMap(List::stream)
                .collect(toList());
    }

    private Optional<Pair<Long, List<PriceProfileAssociationResponse>>> getConnectorAssociations(final Long connectorId) {
        ApiResponse<List<PriceProfileAssociationResponse>> response = execute(priceProfileApi.getCurrentAssociation(connectorId));
        if (response.hasError()) {
            if (HttpStatus.NOT_FOUND.value() == response.getError().getStatusCode()) {
                LOG.warn("Couldn't find price for connectorId {}, returning free of charge", connectorId);
                return Optional.of(Pair.of(connectorId, emptyList()));
            }
            LOG.warn("Unexpected HTTP error {} while fetching price associations for connectorId {}, skipping", response.getError().getStatusCode(), connectorId);
            return Optional.empty();
        }
        return Optional.of(Pair.of(connectorId, response.getResponse()));
    }

    private List<ConnectorPriceProfile> retrieveConnectorPriceIfAny(final Pair<Long, List<PriceProfileAssociationResponse>> pair) {
        if (pair.getRight().isEmpty()) {
            return singletonList(new ConnectorPriceProfile(pair.getLeft(), null));
        }

        return pair.getRight().stream()
                .map(a -> new ProfileReference(a.getOrganizationId(), a.getProfileId()))
                .distinct()
                .map(ref -> fetchProfileAndConvertToConnectorPriceProfile(pair.getLeft(), ref))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(toList());
    }

    private Optional<ConnectorPriceProfile> fetchProfileAndConvertToConnectorPriceProfile(final Long connectorId, final ProfileReference reference) {
        try {
            ApiResponse<PriceProfileResponse> response = execute(priceProfileApi.getPriceProfile(reference.getOrganizationId(), reference.getProfileId()));
            if (response.hasError()) {
                if (HttpStatus.NOT_FOUND.value() == response.getError().getStatusCode()) {
                    LOG.warn("Couldn't find price for connectorId {}, returning free of charge", connectorId);
                    return Optional.of(new ConnectorPriceProfile(connectorId, null));
                }
                LOG.warn("Unexpected HTTP error {} while fetching price for connectorId {}, skipping", response.getError().getStatusCode(), connectorId);
                return Optional.empty();
            }

            PriceProfileResponse profile = response.getResponse();

            // ignore profiles not having source = ALL
            if (!profile.getSource().equals(Source.ALL)) {
                return Optional.empty();
            }

            return Optional.of(new ConnectorPriceProfile(connectorId, profile));
        } catch (Exception e) {
            LOG.warn("Couldn't understand advanced-price, returning free of charge. {}", e.getMessage());
            return Optional.of(new ConnectorPriceProfile(connectorId, null));
        }
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }

    private static class ProfileReference{
        private final Long organizationId;
        private final String profileId;

        public ProfileReference(final Long organizationId, final String profileId) {
            this.organizationId = organizationId;
            this.profileId = profileId;
        }

        public Long getOrganizationId() {
            return organizationId;
        }

        public String getProfileId() {
            return profileId;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final ProfileReference that = (ProfileReference) o;
            return Objects.equals(organizationId, that.organizationId) &&
                    Objects.equals(profileId, that.profileId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(organizationId, profileId);
        }
    }
}
