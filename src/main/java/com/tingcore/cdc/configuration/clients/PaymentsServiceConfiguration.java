package com.tingcore.cdc.configuration.clients;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.payments.ApiClient;
import com.tingcore.payments.api.*;
import okhttp3.Dispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class PaymentsServiceConfiguration {

    @Bean
    public SessionsApi sessionsApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(SessionsApi.class);
    }

    @Bean
    public PaymentaccountsApi paymentaccountsApi(final ApiClient paymentsClient) {
        return notNull(paymentsClient).createService(PaymentaccountsApi.class);
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
