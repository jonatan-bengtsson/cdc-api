package com.tingcore.cdc.configuration;


import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.users.api.UserServiceClient;
import com.tingcore.users.api.v1.*;
import com.tingcore.users.api.v2.*;
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
        return userServiceClient()
                .createService(UsersApi.class);
    }

    @Bean
    public ChargingKeysApi chargingKeysApi() {
        return userServiceClient()
                .createService(ChargingKeysApi.class);
    }

    @Bean
    public CustomersApi customersApi() {
        return userServiceClient()
                .createService(CustomersApi.class);
    }

    @Bean
    public CustomerKeysApi customerKeysApi() {
        return userServiceClient()
                .createService(CustomerKeysApi.class);
    }

    @Bean
    public AttributeValuesApi attributeValuesApi() {
        return userServiceClient()
                .createService(AttributeValuesApi.class);
    }

    @Bean
    public PaymentOptionsApi paymentOptionsApi() {
        return userServiceClient()
                .createService(PaymentOptionsApi.class);
    }

    @Bean
    public CustomerKeyTypesApi customerKeyTypesApi() {
        return userServiceClient()
                .createService(CustomerKeyTypesApi.class);
    }

    @Bean()
    public com.tingcore.users.api.v1.OrganizationsApi organizationsApiV1() {
        return userServiceClient()
                .createService(com.tingcore.users.api.v1.OrganizationsApi.class);
    }


    @Bean
    public com.tingcore.users.api.v2.OrganizationsApi organizationsApi() {
        return userServiceClient()
                .createService(com.tingcore.users.api.v2.OrganizationsApi.class);
    }

    @Bean
    public RolesApi rolesApi() {
        return userServiceClient()
                .createService(RolesApi.class);
    }

    @Bean
    public AttributesApi attributesControllerApi() {
        return userServiceClient()
                .createService(AttributesApi.class);
    }

    @Bean
    public OrganizationModulesApi organizationModulesApi() {
        return userServiceClient()
                .createService(OrganizationModulesApi.class);
    }

    @Bean
    public SystemUsersApi systemUsersApi() {
        return userServiceClient()
                .createService(SystemUsersApi.class);
    }

    @Bean
    public InternalApi internalApi() {
        return userServiceClient()
                .createService(InternalApi.class);
    }

    @Bean
    public PrivacyPoliciesApi privacyPoliciesApi() {
        return userServiceClient()
                .createService(PrivacyPoliciesApi.class);
    }

    @Bean
    public TermsAndConditionsApi termsAndConditionsApi() {
        return userServiceClient()
                .createService(TermsAndConditionsApi.class);
    }

    @Value("${app.user-service.base-url}")
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }
}
