package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.payments.cpo.ApiClient;
import com.tingcore.payments.cpo.api.*;
import okhttp3.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Optional;

import static com.tingcore.cdc.constant.SpringProfilesConstant.SESSION_HISTORY_V2;
import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class PaymentsServiceConfiguration {
    @Bean
    public ChargesApi chargesApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(ChargesApi.class);
    }

    @Bean
    public SessionsApi sessionsApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(SessionsApi.class);
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
    @Profile("!" + SESSION_HISTORY_V2)
    public SessionHistoryApi sessionhistoryApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(SessionHistoryApi.class);
    }

    @Bean
    public ReceiptsApi receiptApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(ReceiptsApi.class);
    }

    @Bean
    public ApiClient paymentsClient(final Environment environment, Optional<Tracing> httpTracing) {
        notNull(environment);
        final ApiClient paymentsClient = new ApiClient();
        String baseUrl = notNull(environment.getRequiredProperty("app.payments-service.base-url"));
        paymentsClient.getAdapterBuilder().baseUrl(baseUrl);
        httpTracing.ifPresent(tracing -> instrumentJavaClient(paymentsClient, tracing));
        return paymentsClient;
    }

    private void instrumentJavaClient(ApiClient paymentsClient, Tracing httpTracing) {
        Dispatcher dispatcher = new Dispatcher(
                httpTracing
                        .currentTraceContext()
                        .executorService(new Dispatcher().executorService())
        );

        paymentsClient.getOkBuilder()
                .dispatcher(dispatcher)
                .addNetworkInterceptor(TracingInterceptor.create(httpTracing));
    }
}
