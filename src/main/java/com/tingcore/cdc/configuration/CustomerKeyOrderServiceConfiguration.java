package com.tingcore.cdc.configuration;

import com.tingcore.customerkeyorder.client.ApiClient;
import com.tingcore.customerkeyorder.client.CustomerKeyOrderServiceApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerKeyOrderServiceConfiguration {

    private final String baseUrl;

    public CustomerKeyOrderServiceConfiguration(@Value("${app.customer-key-order-service.base-url}") final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Bean
    public CustomerKeyOrderServiceApi customerKeyOrderServiceApi(ApiClient client) {
        return client.createService(CustomerKeyOrderServiceApi.class);
    }

    @Bean
    public ApiClient customerKeyOrderClient() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(baseUrl);
        return client;
    }

}
