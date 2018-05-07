package com.tingcore.cdc.configuration;

import com.tingcore.campaign.client.CampaignServiceApi;
import com.tingcore.campaign.client.CampaignServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public CampaignServiceClient campaignServiceClient() {
        return CampaignServiceClient.create()
                .baseUrl(baseUrl)
                .connectionTimeout(defaultTimeOut, TimeUnit.SECONDS)
                .build();
    }

}
