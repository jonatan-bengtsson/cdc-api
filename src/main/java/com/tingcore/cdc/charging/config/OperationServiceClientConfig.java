package com.tingcore.cdc.charging.config;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.charging.operations.ApiClient;
import com.tingcore.charging.operations.api.OperationsApi;
import okhttp3.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Configuration
public class OperationServiceClientConfig {

    private final String chargingOperationsServiceBaseUrl;

    public OperationServiceClientConfig(Environment env) {
        this.chargingOperationsServiceBaseUrl = env.getRequiredProperty("app.charging-operations-service.base-url");
    }

    @Bean
    public OperationsApi createOperationsApi(ApiClient client) {
        return client.createService(OperationsApi.class);
    }

    @Bean
    public ApiClient chargingOperationServiceClient(Optional<Tracing> httpTracing) {
        ApiClient client = new ApiClient();
        client
                .getAdapterBuilder()
                .baseUrl(chargingOperationsServiceBaseUrl);

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
