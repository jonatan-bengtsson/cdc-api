package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.payments.debt.collector.DebtCollectClient;
import com.tingcore.payments.debt.collector.api.v1.DebtCollectorRestApi;
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
public class DebtCollectConfiguration {

    private final String BASE_URL;
    private final Long DEFAULT_TIMEOUT;
    private final Environment ENV;


    public DebtCollectConfiguration(@Value("${app.debt-collect.base-url}") String baseUrl,
                                    @Value("${app.debt-collect.default-timeout:20}") Long defaultTimeout,
                                    final Environment env) {
        BASE_URL = notNull(baseUrl);
        DEFAULT_TIMEOUT = notNull(defaultTimeout);
        ENV = notNull(env);
    }

    @Bean
    DebtCollectClient debtCollectClient(final Optional<Tracing> tracing) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        tracing.ifPresent(httpTracing -> instrumentClient(builder, httpTracing));
        return DebtCollectClient
                .create()
                .baseUrl(BASE_URL)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .loggingLevel(ENV.acceptsProfiles(DEV) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)
                .build();
    }

    @Bean
    public DebtCollectorRestApi debtCollectorRestApi(final DebtCollectClient debtCollectClient) {
        return notNull(debtCollectClient).createService(DebtCollectorRestApi.class);
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
