package com.tingcore.cdc.charging.config;

import com.tingcore.charging.operations.ApiClient;
import com.tingcore.charging.operations.api.OperationsApi;
import org.springframework.context.annotation.Bean;

public class OperationServiceClientConfig {


    @Bean
    public OperationsApi createOperationsApi() {
        ApiClient client = new ApiClient();

        //client.setBasePath("http://localhost:8080");
        client.getAdapterBuilder().baseUrl("https://charging-operations.tingcore-test.com");
        return client.createService(OperationsApi.class);
    }

}
