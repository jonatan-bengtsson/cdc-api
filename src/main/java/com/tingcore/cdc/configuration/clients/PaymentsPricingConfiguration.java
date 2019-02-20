package com.tingcore.cdc.configuration.clients;

import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.emp.pricing.client.PricingClient;
import com.tingcore.emp.pricing.client.api.v1.PricingProfileRestApi;
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

import static com.tingcore.cdc.constant.SpringProfilesConstant.DEV;
import static com.tingcore.cdc.constant.SpringProfilesConstant.PRICE_PROFILES_FROM_REST;

@Configuration
@Profile(PRICE_PROFILES_FROM_REST)
public class PaymentsPricingConfiguration {

    private final String baseUrl;
    private final Long timeout;
    private final Environment environment;


    public PaymentsPricingConfiguration(
            @Value("${app.payments-pricing.base-url}") final String baseUrl,
            @Value("${app.payments-pricing.default-timeout:20}") final Long timeout,
            final Environment environment) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        this.environment = environment;
    }

    @Bean
    public PricingClient paymentsPricingClient(Optional<Tracing> tracing) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        tracing.ifPresent(httpTracing -> instrumentClient(builder, httpTracing));
        return PricingClient
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
    public PricingProfileRestApi paymentsPricingRestApi(PricingClient client) {
        return client
                .createService(PricingProfileRestApi.class);
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
