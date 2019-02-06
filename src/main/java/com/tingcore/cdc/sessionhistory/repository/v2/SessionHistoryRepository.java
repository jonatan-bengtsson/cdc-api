package com.tingcore.cdc.sessionhistory.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.sessionhistory.repository.GetSessionHistoryException;
import com.tingcore.payments.sessionstasher.models.v1.Session;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Repository
public class SessionHistoryRepository {
    private static final String ES_INDEX = "sessions";
    private static final Integer DEFAULT_TIME_OUT = 60;
    private static final Logger logger = LoggerFactory.getLogger(SessionHistoryRepository.class);

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public SessionHistoryRepository(final ObjectMapper objectMapper,
                                    final RestHighLevelClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    public List<Session> getSession(final Long chargingKeyId, final Long from, final Long to) {
        final BoolQueryBuilder query = QueryBuilders.boolQuery()
                .must(termQuery("identity.chargingKey.id", chargingKeyId))
                .filter(rangeQuery("startedAt")
                        .gte(Instant.ofEpochMilli(from))
                        .format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
                .filter(rangeQuery("stoppedAt")
                        .lte(Instant.ofEpochMilli(to))
                        .format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .query(query);
        final SearchRequest request = new SearchRequest(ES_INDEX)
                .source(sourceBuilder);

        final SearchResponse response = execute(request);
        return Arrays.stream(response.getHits().getHits())
                .map(this::map)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private SearchResponse execute(final SearchRequest request) {
        final CompletableFuture<SearchResponse> future = new CompletableFuture<>();
        client.searchAsync(request, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                future.complete(searchResponse);
            }

            @Override
            public void onFailure(Exception e) {
                future.completeExceptionally(e);
            }
        });

        try {
            return future.get(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("Failed to complete the search.", e);
            throw new GetSessionHistoryException();
        }
    }

    private Optional<Session> map(final SearchHit hit) {
        try {
            return Optional.of(objectMapper.readValue(hit.getSourceAsString(), Session.class));
        } catch (IOException e) {
            logger.warn("Failed to deserialize session.", e);
            return Optional.empty();
        }
    }
}
