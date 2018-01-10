package com.tingcore.cdc.configuration;

import com.tingcore.cdc.configuration.IntegrationConfiguration.PaymentsServiceInformation;
import com.tingcore.payments.cpo.api.PricesApi;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.api.TokensApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class PaymentsServiceConfiguration {
    @Bean
    public ChargesApi chargesApi(final com.tingcore.payments.emp.ApiClient paymentsClient) {
        return new ChargesApi(notNull(paymentsClient));
    }

    @Bean
    public TokensApi tokensApi(final com.tingcore.payments.emp.ApiClient paymentsClient) {
        return new TokensApi(notNull(paymentsClient));
    }

    @Bean
    public PricesApi pricesApi(final com.tingcore.payments.cpo.ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(PricesApi.class);
    }

    @Bean
    public com.tingcore.payments.emp.ApiClient paymentsProviderClient(final RestTemplate restTemplate,
                                                                      final PaymentsServiceInformation paymentsServiceInformation) {
        final com.tingcore.payments.emp.ApiClient paymentsClient = new com.tingcore.payments.emp.ApiClient(restTemplate);
        paymentsClient.setBasePath("http://" + notNull(paymentsServiceInformation).getHostname());
        paymentsClient.setUserAgent("cdc/1");
        return paymentsClient;
    }

    @Bean
    public com.tingcore.payments.cpo.ApiClient paymentsOperatorClient(final PaymentsServiceInformation paymentsServiceInformation) {
        final com.tingcore.payments.cpo.ApiClient paymentsClient = new com.tingcore.payments.cpo.ApiClient();
        String baseUrl = ("http://" + notNull(paymentsServiceInformation).getHostname());
        paymentsClient.getAdapterBuilder().baseUrl(baseUrl);
        return paymentsClient;
    }
}
