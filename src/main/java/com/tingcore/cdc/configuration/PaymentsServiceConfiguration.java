package com.tingcore.cdc.configuration;

import com.tingcore.payments.cpo.ApiClient;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.cpo.api.ChargesApi;
import com.tingcore.payments.cpo.api.PaymentaccountsApi;
import com.tingcore.payments.cpo.api.TokensApi;
import com.tingcore.payments.cpo.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class PaymentsServiceConfiguration {
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

    @Bean
    public PaymentaccountsApi paymentaccountsApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(PaymentaccountsApi.class);
    }

    @Bean
    public SessionhistoryApi sessionhistoryApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(SessionhistoryApi.class);
    }

    @Bean
    public ApiClient paymentsClient(final Environment environment) {
        notNull(environment);
        final ApiClient paymentsClient = new ApiClient();
        String baseUrl = notNull(environment.getRequiredProperty("app.payments-service.base-url"));
        paymentsClient.getAdapterBuilder().baseUrl(baseUrl);
        return paymentsClient;
    }
}