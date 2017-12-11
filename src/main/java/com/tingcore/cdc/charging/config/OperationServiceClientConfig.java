package com.tingcore.cdc.charging.config;

import com.tingcore.charging.operations.ApiClient;
import com.tingcore.charging.operations.api.OperationsApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OperationServiceClientConfig {


    private final String chargingOperationsServiceBaseUrl;

    public OperationServiceClientConfig(Environment env) {
        this.chargingOperationsServiceBaseUrl = env.getRequiredProperty("app.charging-operations-service.base-url");
    }

    @Bean
    public OperationsApi createOperationsApi() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(this.chargingOperationsServiceBaseUrl);
        return client.createService(OperationsApi.class);
    }

}
