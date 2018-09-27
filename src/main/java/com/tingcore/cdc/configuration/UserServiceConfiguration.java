package com.tingcore.cdc.configuration;


import brave.Tracing;
import brave.okhttp3.TracingInterceptor;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.users.api.UserServiceClient;
import com.tingcore.users.api.v1.*;
import com.tingcore.users.api.v2.*;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;
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
    public UserServiceClient userServiceClient(Optional<Tracing> httpTracing) {
        UserServiceClient.Builder clientBuilder = UserServiceClient
                .createBuilder()
                .baseUrl(baseUrl)
                .loggingLevel(environment.acceptsProfiles(SpringProfilesConstant.DEV) ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE)
                .readTimeout(defaultTimeOut.longValue(), TimeUnit.SECONDS)
                .connectionTimeout(defaultTimeOut.longValue(), TimeUnit.SECONDS);

        httpTracing.ifPresent(tracing -> {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .dispatcher(
                            new Dispatcher(
                                    tracing
                                            .currentTraceContext()
                                            .executorService(new Dispatcher().executorService())
                            )
                    )
                    .addNetworkInterceptor(TracingInterceptor.create(tracing));
            clientBuilder.okHttpClient(okHttpBuilder);
        });

        return clientBuilder.build();
    }

    @Value("${app.user-service.base-url}")
    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }


    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    @Bean
    public UsersApi userControllerApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(UsersApi.class);
    }

    @Bean
    public ChargingKeysApi chargingKeysApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(ChargingKeysApi.class);
    }

    @Bean
    public CustomersApi customersApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(CustomersApi.class);
    }

    @Bean
    public CustomerKeysApi customerKeysApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(CustomerKeysApi.class);
    }

    @Bean
    public AttributeValuesApi attributeValuesApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(AttributeValuesApi.class);
    }

    @Bean
    public PaymentOptionsApi paymentOptionsApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(PaymentOptionsApi.class);
    }

    @Bean
    public CustomerKeyTypesApi customerKeyTypesApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(CustomerKeyTypesApi.class);
    }

    @Bean()
    public com.tingcore.users.api.v1.OrganizationsApi organizationsApiV1(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(com.tingcore.users.api.v1.OrganizationsApi.class);
    }


    @Bean
    public com.tingcore.users.api.v2.OrganizationsApi organizationsApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(com.tingcore.users.api.v2.OrganizationsApi.class);
    }

    @Bean
    public RolesApi rolesApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(RolesApi.class);
    }

    @Bean
    public AttributesApi attributesControllerApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(AttributesApi.class);
    }

    @Bean
    public OrganizationModulesApi organizationModulesApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(OrganizationModulesApi.class);
    }

    @Bean
    public SystemUsersApi systemUsersApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(SystemUsersApi.class);
    }

    @Bean
    public InternalOrganizationApi internalOrganizationApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(InternalOrganizationApi.class);
    }

    @Bean
    public PrivacyPoliciesApi privacyPoliciesApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(PrivacyPoliciesApi.class);
    }

    @Bean
    public TermsAndConditionsApi termsAndConditionsApi(UserServiceClient userServiceClient) {
        return userServiceClient
                .createService(TermsAndConditionsApi.class);
    }

    @Bean
    public InternalAgreementsApi internalAgreementsApi(UserServiceClient userServiceClient){
        return userServiceClient
                .createService(InternalAgreementsApi.class);
    }
}
