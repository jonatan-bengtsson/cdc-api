package com.tingcore.cdc.charging.config;

import com.tingcore.charging.assets.ApiClient;
import com.tingcore.charging.assets.api.ApiForPaymentsApi;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class AssetServiceClientConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AssetServiceClientConfig.class);

    private final String assetServiceBaseUrl;

    public AssetServiceClientConfig(Environment env) {
        this.assetServiceBaseUrl = env.getRequiredProperty("app.asset-service.base-url");
    }

    @Bean
    public ChargeSitesApi createChargeSitesApi() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(this.assetServiceBaseUrl);
        LOG.info("URL for Asset for ChargeSiteController: {}", this.assetServiceBaseUrl);
        return client.createService(ChargeSitesApi.class);
    }

    @Bean
    public ApiForPaymentsApi createApiForPaymentsApi() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(this.assetServiceBaseUrl);
        LOG.info("URL for Asset for PaymentsApi: {}", this.assetServiceBaseUrl);
        return client.createService(ApiForPaymentsApi.class);
    }
}
