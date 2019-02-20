package com.tingcore.cdc.configuration.clients;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.customerkeyorder.client.ApiClient;
import com.tingcore.customerkeyorder.client.CustomerKeyOrderServiceApi;
import okhttp3.Dispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

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
    public ApiClient customerKeyOrderClient(Optional<Tracing> httpTracing) {


        ApiClient client = new ApiClient();
        client
                .getAdapterBuilder()
                .baseUrl(baseUrl);

        httpTracing.ifPresent(tracing -> {
            Dispatcher dispatcher = new Dispatcher(
                    tracing
                            .currentTraceContext()
                            .executorService(new Dispatcher().executorService())
            );
            client
                    .getOkBuilder()
                    .dispatcher(dispatcher)
                    .addNetworkInterceptor(TracingInterceptor.create(tracing));
        });

        return client;
    }

}
