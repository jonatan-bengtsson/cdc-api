package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.emp.pricing.client.PricingClient;
import com.tingcore.payments.debttracker.DebtTrackerClient;
import com.tingcore.payments.debttracker.api.v1.DebttrackerRestApi;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.tingcore.cdc.constant.SpringProfilesConstant.DEV;
import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class DebtTrackerConfiguration {

    private final String baseUrl;
    private final Long timeout;
    private final Environment environment;

    public DebtTrackerConfiguration(@Value("${app.debt-tracker.base-url}") final String baseUrl,
                                    @Value("${app.debt-tracker.default-timeout:20}") final Long timeout,
                                    final Environment environment) {
        this.baseUrl = notNull(baseUrl);
        this.timeout = notNull(timeout);
        this.environment = notNull(environment);
    }

    @Bean
    public DebtTrackerClient paymentsPricingClient(Optional<Tracing> tracing) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        tracing.ifPresent(httpTracing -> instrumentClient(builder, httpTracing));
        return DebtTrackerClient
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
    public DebttrackerRestApi debttrackerRestApi(DebtTrackerClient client) {
        return client.createService(DebttrackerRestApi.class);
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
