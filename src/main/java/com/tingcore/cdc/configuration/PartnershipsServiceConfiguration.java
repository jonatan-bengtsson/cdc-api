package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.partnerships.client.PartnershipsServiceClient;
import com.tingcore.partnerships.client.PartnershipsServiceApi;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
public class PartnershipsServiceConfiguration {

    private final String baseUrl;
    private final Long defaultTimeout;

    public PartnershipsServiceConfiguration(@Value("${app.partnerships-service.base-url}") final String baseUrl,
                                            @Value("${app.partnerships-service.default-timeout}") final Long defaultTimeout) {
        this.baseUrl = baseUrl;
        this.defaultTimeout = defaultTimeout;
    }

    @Bean
    public PartnershipsServiceApi partnershipsServiceApi(PartnershipsServiceClient partnershipsServiceClient) {
        return partnershipsServiceClient.createService(PartnershipsServiceApi.class);
    }

    @Bean
    public PartnershipsServiceClient partnershipsServiceClient(Optional<Tracing> httpTracing) {
        PartnershipsServiceClient.Builder clientBuilder = PartnershipsServiceClient.create()
                .baseUrl(baseUrl)
                .connectionTimeout(defaultTimeout, TimeUnit.SECONDS)
                .readTimeout(defaultTimeout, TimeUnit.SECONDS)
                .writeTimeout(defaultTimeout, TimeUnit.SECONDS);

        httpTracing.ifPresent(tracing -> {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                    .dispatcher(new Dispatcher(
                            tracing.currentTraceContext()
                            .executorService(new Dispatcher().executorService())
                    ))
                    .addNetworkInterceptor(TracingInterceptor.create(tracing));
            clientBuilder.okHttpClient(okBuilder);
        });

        return clientBuilder.build();
    }
}
