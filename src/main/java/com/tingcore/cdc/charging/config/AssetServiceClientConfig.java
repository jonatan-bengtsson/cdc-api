package com.tingcore.cdc.charging.config;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.charging.assets.ApiClient;
import com.tingcore.charging.assets.api.ApiForPaymentsApi;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import okhttp3.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;


@Configuration
public class AssetServiceClientConfig {

    private final String assetServiceBaseUrl;

    public AssetServiceClientConfig(Environment env) {
        this.assetServiceBaseUrl = env.getRequiredProperty("app.asset-service.base-url");
    }

    @Bean
    public ChargeSitesApi createChargeSitesApi(ApiClient apiClient) {
        return apiClient.createService(ChargeSitesApi.class);
    }

    @Bean
    public ApiForPaymentsApi createApiForPaymentsApi(ApiClient apiClient) {
        return apiClient.createService(ApiForPaymentsApi.class);
    }

    @Bean
    public ApiClient assetServiceClient(Optional<Tracing> httpTracing) {
        ApiClient client = new ApiClient();
        client
                .getAdapterBuilder()
                .baseUrl(assetServiceBaseUrl);

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
