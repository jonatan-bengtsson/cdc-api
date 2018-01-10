package com.tingcore.cdc.configuration;


import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.users.ApiClient;
import com.tingcore.users.api.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class UserServiceConfiguration {

    private String baseUrl;
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

    @Value("${app.user-service.base-url}")
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
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