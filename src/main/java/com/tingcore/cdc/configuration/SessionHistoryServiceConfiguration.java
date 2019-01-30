package com.tingcore.cdc.configuration;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.sessions.history.client.SessionHistoryClient;
import com.tingcore.sessions.history.client.api.v1.SessionHistoryRestApi;
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
import static org.apache.commons.lang3.Validate.notBlank;

@Configuration
public class SessionHistoryServiceConfiguration {

    private final String baseUrl;
    private final Long timeout;
    private final Environment environment;

    public SessionHistoryServiceConfiguration(
            @Value("${app.session-history-service.base-url}") final String baseUrl,
            @Value("${app.session-history-service.default-timeout:20}") final Long timeout,
            final Environment environment) {
        this.baseUrl = notBlank(baseUrl);
        this.timeout = timeout;
        this.environment = environment;
    }

    @Bean
    public SessionHistoryClient sessionHistoryClient(Optional<Tracing> tracing) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        tracing.ifPresent(httpTracing -> instrumentClient(builder, httpTracing));
        return SessionHistoryClient
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
    public SessionHistoryRestApi sessionHistoryRestApi(SessionHistoryClient client) {
        return client.createService(SessionHistoryRestApi.class);
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
