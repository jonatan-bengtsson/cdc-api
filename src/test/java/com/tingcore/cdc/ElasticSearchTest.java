package com.tingcore.cdc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic;
import pl.allegro.tech.embeddedelasticsearch.PopularProperties;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class ElasticSearchTest {
    private static final String TYPE = "_doc";

    private static EmbeddedElastic embeddedElastic;
    protected static RestHighLevelClient client;

    protected static void setup(final String index, final String type, final Map<Object, String> testData) {
        final EmbeddedElastic.Builder builder = EmbeddedElastic.builder()
                .withElasticVersion("6.2.4")
                .withSetting(PopularProperties.CLUSTER_NAME, "cnd");

        builder.withIndex(index);

        try {
            embeddedElastic = builder.withStartTimeout(30, TimeUnit.SECONDS)
                    .build()
                    .start();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", embeddedElastic.getHttpPort(), "http")));

        final BulkRequest bulkRequest = new BulkRequest();
        testData.forEach((id, doc) -> bulkRequest.add(new IndexRequest(index, type, id.toString()).source(doc, XContentType.JSON)));
        try {
            client.bulk(bulkRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        embeddedElastic.refreshIndices();
    }

    @BeforeClass
    public static void start() {
        try {
            embeddedElastic = EmbeddedElastic.builder()
                    .withElasticVersion("6.2.4")
                    .withSetting(PopularProperties.CLUSTER_NAME, "cnd")
                    .build()
                    .start();

            client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", embeddedElastic.getHttpPort(), "http")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void tearDown() {
        embeddedElastic.stop();
    }

    protected void createIndex(final String index, final String indexMappings, final String indexSettings) throws Exception {
        final GetIndexRequest request = new GetIndexRequest();
        request.indices(index);

        if (!client.indices().exists(request)) {
            client.indices().create(
                    new CreateIndexRequest(index)
                            .mapping("_doc", indexMappings, XContentType.JSON)
                            .settings(indexSettings, XContentType.JSON)
            );
        }
    }

    protected void indexData(final String index, final Map<Object, String> testData) throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        testData.forEach((id, doc) -> bulkRequest.add(new IndexRequest(index, TYPE, id.toString()).source(doc, XContentType.JSON)));
        client.bulk(bulkRequest);
    }
}
