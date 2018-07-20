package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.campaign.client.CampaignServiceApi;
import com.tingcore.campaign.client.CampaignServiceClient;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
public class CampaignServiceConfiguration {

    private final String baseUrl;
    private final Long defaultTimeOut;

    public CampaignServiceConfiguration(
            @Value("${app.campaign-service.base-url}") final String baseUrl,
            @Value("${app.campaign-service.default-timeout}") final Long defaultTimeOut) {
        this.baseUrl = baseUrl;
        this.defaultTimeOut = defaultTimeOut;
    }

    @Bean
    public CampaignServiceApi campaignServiceApi(CampaignServiceClient client) {
        return client.createService(CampaignServiceApi.class);
    }

    @Bean
    public CampaignServiceClient campaignServiceClient(Optional<Tracing> httpTracing) {
        CampaignServiceClient.Builder clientBuilder = CampaignServiceClient.create()
                .baseUrl(baseUrl)
                .connectionTimeout(defaultTimeOut, TimeUnit.SECONDS)
                .readTimeout(defaultTimeOut, TimeUnit.SECONDS)
                .writeTimeout(defaultTimeOut, TimeUnit.SECONDS);

        httpTracing.ifPresent(tracing -> {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                    .dispatcher(
                            new Dispatcher(
                                    tracing
                                            .currentTraceContext()
                                            .executorService(new Dispatcher().executorService())
                            )
                    )
                    .addNetworkInterceptor(TracingInterceptor.create(tracing));
            clientBuilder.okHttpClient(okBuilder);
        });

        return clientBuilder.build();
    }

}
