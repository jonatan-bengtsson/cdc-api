package com.tingcore.cdc.charging.config;

import com.tingcore.charging.operations.ApiClient;
import com.tingcore.charging.operations.api.OperationsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OperationServiceClientConfig {

    private static final Logger LOG = LoggerFactory.getLogger(OperationServiceClientConfig.class);


    private final String chargingOperationsServiceBaseUrl;

    public OperationServiceClientConfig(Environment env) {
        this.chargingOperationsServiceBaseUrl = env.getRequiredProperty("app.charging-operations-service.base-url");
    }

    @Bean
    public OperationsApi createOperationsApi() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(this.chargingOperationsServiceBaseUrl);
        LOG.info("URL for Asset for OperationService: {}", this.chargingOperationsServiceBaseUrl);


        return client.createService(OperationsApi.class);
    }

}
