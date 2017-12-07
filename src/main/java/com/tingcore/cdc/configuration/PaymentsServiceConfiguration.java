package com.tingcore.cdc.configuration;

import com.tingcore.cdc.configuration.IntegrationConfiguration.PaymentsServiceInformation;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.emp.ApiClient;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.api.TokensApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class PaymentsServiceConfiguration {
    @Bean
    public ApiClient paymentsClient(final PaymentsServiceInformation paymentsServiceInformation) {
        final ApiClient paymentsClient = new ApiClient();
        paymentsClient.getAdapterBuilder().baseUrl("http://" + notNull(paymentsServiceInformation).getHostname());
        return paymentsClient;
    }

    @Bean
    public ChargesApi chargesApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(ChargesApi.class);
    }

    @Bean
    public TokensApi tokensApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(TokensApi.class);
    }

    @Bean
    public PricesApi pricesApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(PricesApi.class);
    }
}
