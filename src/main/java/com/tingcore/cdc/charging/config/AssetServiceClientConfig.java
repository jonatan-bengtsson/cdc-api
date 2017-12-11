package com.tingcore.cdc.charging.config;

import com.tingcore.charging.assets.ApiClient;
import com.tingcore.charging.assets.api.ChargePointModelsApi;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class AssetServiceClientConfig {

    private final String assetServiceBaseUrl;

    public AssetServiceClientConfig(Environment env) {
        this.assetServiceBaseUrl = env.getRequiredProperty("app.asset-service.base-url");
    }

    @Bean
    public ChargeSitesApi createChargeSiteControllerApi() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl("https://asset-service-test.tingcore-infra.com");
        client.getAdapterBuilder().baseUrl(this.assetServiceBaseUrl);
        return client.createService(ChargeSitesApi.class);
    }

}
