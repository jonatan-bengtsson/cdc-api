package com.tingcore.cdc.charging.config;

import com.tingcore.charging.assets.ApiClient;
import com.tingcore.charging.assets.api.ChargePointModelsApi;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AssetServiceClientConfig {

    @Bean
    public ChargeSitesApi createChargeSiteControllerApi() {

        ApiClient client = new ApiClient();
        //client.getAdapterBuilder().baseUrl("http://localhost:8888");
        client.getAdapterBuilder().baseUrl("https://asset-service-test.tingcore-infra.com");
        return client.createService(ChargeSitesApi.class);
    }

    @Bean
    public ChargePointModelsApi createChargePointTypeControllerApi() {
        ApiClient client = new ApiClient();

        //client.setBasePath("http://localhost:8080");
        client.getAdapterBuilder().baseUrl("https://asset-service-test.tingcore-infra.com");
        return client.createService(ChargePointModelsApi.class);
    }
}
