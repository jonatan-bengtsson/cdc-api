package com.tingcore.cdc.configuration;


import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.users.ApiClient;
import com.tingcore.users.api.*;
import com.tingcore.users.api.v2.ChargingKeysApi;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.TimeUnit;

@Configuration
public class UserServiceConfiguration {

    private String baseUrl;
    private Integer defaultTimeOut;
    private Environment environment;

    public UserServiceConfiguration(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ApiClient userServiceApiClient() {
        ApiClient client = new ApiClient();
        client.getAdapterBuilder().baseUrl(baseUrl);
        configureOkHttpClient(client);
        return client;
    }

    @Bean
    public UserServiceClient userServiceClient() {
        return UserServiceClient
                .createBuilder()
                .baseUrl(baseUrl)
                .loggingLevel(environment.acceptsProfiles(SpringProfilesConstant.DEV) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)
                .readTimeout(defaultTimeOut.longValue(), TimeUnit.SECONDS)
                .connectionTimeout(defaultTimeOut.longValue(), TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public UsersApi userControllerApi() {
        return userServiceApiClient().createService(UsersApi.class);
    }

    @Bean
    public OrganizationsApi organizationControllerApi() {
        return userServiceApiClient().createService(OrganizationsApi.class);
    }

    @Bean
    public CustomerKeysApi customerKeysApi() {
        return userServiceApiClient().createService(CustomerKeysApi.class);
    }

    @Bean
    public PaymentOptionsApi paymentOptionsApi() {
        return userServiceApiClient().createService(PaymentOptionsApi.class);
    }

    @Bean
    public AttributesApi provideAttributesControllerApi() {
        return userServiceApiClient().createService(AttributesApi.class);
    }

    @Bean
    public ChargingKeysApi chargingKeysApi() {
        return userServiceClient()
                .createService(ChargingKeysApi.class);
    }

    @Value("${app.user-service.base-url}")
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    private void configureOkHttpClient(final ApiClient client) {
        if (environment.acceptsProfiles(SpringProfilesConstant.DEV)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.getOkBuilder().addInterceptor(logging);
        }
    }

}
