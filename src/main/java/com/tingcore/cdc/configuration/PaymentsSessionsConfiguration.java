package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.sessions.client.PaymentsSessionsClient;
import com.tingcore.sessions.client.api.v1.PaymentsSessionsRestApi;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.tingcore.cdc.constant.SpringProfilesConstant.CHARGING_SESSIONS_V2;
import static com.tingcore.cdc.constant.SpringProfilesConstant.DEV;

@Configuration
@Profile(CHARGING_SESSIONS_V2)
public class PaymentsSessionsConfiguration {

    private final String baseUrl;
    private final Long timeout;
    private final Environment environment;


    public PaymentsSessionsConfiguration(
            @Value("${app.payments-sessions.base-url}") final String baseUrl,
            @Value("${app.payments-sessions.default-timeout}") final Long timeout,
            final Environment environment) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        this.environment = environment;
    }


    @Bean
    public PaymentsSessionsClient paymentsSessionsClient(Optional<Tracing> tracing) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        tracing.ifPresent(httpTracing -> instrumentClient(builder, httpTracing));
        return PaymentsSessionsClient
                .create()
                .okHttpClient(builder)
                .baseUrl(baseUrl)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectionTimeout(timeout, TimeUnit.SECONDS)
                .loggingLevel(environment.acceptsProfiles(DEV) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)
                .build();
    }


    @Bean
    public PaymentsSessionsRestApi paymentsSessionsRestApi(PaymentsSessionsClient client) {
        return client
                .createService(PaymentsSessionsRestApi.class);
    }

    private void instrumentClient(OkHttpClient.Builder builder, Tracing tracing) {
        Dispatcher dispatcher = new Dispatcher(
                tracing
                        .currentTraceContext()
                        .executorService(new Dispatcher().executorService())
        );

        builder
                .dispatcher(dispatcher)
                .addNetworkInterceptor(TracingInterceptor.create(tracing));
    }
}
