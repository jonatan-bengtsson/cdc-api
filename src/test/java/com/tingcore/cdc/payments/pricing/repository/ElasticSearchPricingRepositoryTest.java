package com.tingcore.cdc.payments.pricing.repository;

import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.ElasticSearchTest;
import com.tingcore.cdc.payments.pricing.model.ConnectorPriceProfile;
import com.tingcore.commons.core.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ElasticSearchPricingRepositoryTest extends ElasticSearchTest {

    private static boolean isTestDataLoaded = false;
    private ElasticSearchPricingRepository repository = new ElasticSearchPricingRepository(client, JsonUtils.getObjectMapper());

    @Before
    public void setUp() throws Exception {
        if (isTestDataLoaded) {
            return;
        }

        createIndex("price-profiles",
                "{\"_doc\": {\"properties\": {\"id\": {\"type\": \"keyword\"},\"createdAt\": {\"type\": \"date\",\"format\": \"epoch_millis\"},\"currency\": {\"type\": \"keyword\"},\"source\": {\"type\": \"keyword\"},\"organizationId\": {\"type\": \"keyword\"}}}}",
                "{}");
        indexData("price-profiles",
                new ImmutableMap.Builder<Object, String>()
                        .put("1_3268ad45fbc7301c", "{\"organizationId\": \"1\",\"createdAt\": \"1550226989712\",\"name\": \"FortumCharge&DriveBV284\", \"description\": \"MigratedbyMatthew3\",  \"currency\": \"SEK\",\"id\": \"3268ad45fbc7301c\",\"source\": \"ALL\",\"content\": {\"segments\": [{\"price_components\": [{\"type\": \"TIME\",\"price\":100,\"step_size\": 60}]}]}}")
                        .put("1_4b5e67a7bbe71ca3", "{\"organizationId\": \"1\",\"createdAt\": \"1550584659417\",\"name\": \"Laddregion Malardalen686\",\"description\": \"Migrated from legacy\",\"currency\": \"SEK\",\"id\": \"4b5e67a7bbe71ca3\",\"source\": \"ALL\",\"content\": {\"segments\": [{\"price_components\": [{\"type\": \"TIME\",\"price\":  0,\"step_size\": 60}]}]}}")
                        .build());

        createIndex("price-profile-associations",
                "{\"_doc\": {\"properties\": {\"partitionKey\": {\"type\": \"keyword\"},\"validFrom\": {\"type\": \"date\",\"format\": \"epoch_millis\"},\"createdAt\": {\"type\": \"date\",\"format\": \"epoch_millis\"},\"profileId\": {\"type\": \"keyword\"},\"source\": {\"type\": \"keyword\"},\"organizationId\": {\"type\": \"keyword\"},\"connectorId\": {\"type\": \"keyword\"}}}}",
                "{}");

        indexData("price-profile-associations",
                new ImmutableMap.Builder<Object, String>()
                        .put("1525355382000_OZwFWUq", "{\"organizationId\": \"1\",\"createdAt\": \"1550227091972\",\"partitionKey\": \"OZwFWUq\",\"connectorId\": \"203\",\"profileId\": \"3268ad45fbc7301c\",\"source\": \"ALL\",\"validFrom\": \"1525355382000\"}")
                        .put("1525355382000_X32TvU3", "{\"organizationId\": \"1\",\"createdAt\": \"1550227090892\",\"partitionKey\": \"X32TvU3\",\"connectorId\": \"200\",\"profileId\": \"3268ad45fbc7301c\",\"source\": \"ALL\",\"validFrom\": \"1525355382000\"}")
                        .put("1515355382000_wohoooo", "{\"organizationId\": \"1\",\"createdAt\": \"1550227090892\",\"partitionKey\": \"wohoooo\",\"connectorId\": \"200\",\"profileId\": \"oldProfileId\",    \"source\": \"ALL\",\"validFrom\": \"1515355382000\"}")
                        .build()
        );

        Thread.sleep(1500);
        isTestDataLoaded = true;
    }

    @Test
    public void testRetrieveProfilesForConnectors() {
        List<ConnectorPriceProfile> result = repository.getCurrentProfileForConnectors(asList(200L, 203L, 99L));

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getConnectorId()).isEqualTo(200L);
        assertThat(result.get(0).getProfile().get().getId()).isEqualTo("3268ad45fbc7301c");
        assertThat(result.get(1).getConnectorId()).isEqualTo(203L);
        assertThat(result.get(1).getProfile().get().getId()).isEqualTo("3268ad45fbc7301c");
        assertThat(result.get(2).getConnectorId()).isEqualTo(99L);
        assertThat(result.get(2).getProfile().isPresent()).isFalse();
    }
}
