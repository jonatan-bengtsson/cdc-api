package com.tingcore.cdc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.crm.repository.v2.PrivacyPolicyRepository;
import com.tingcore.cdc.crm.repository.v2.TermsAndConditionRepository;
import com.tingcore.users.api.UserServiceClient;
import com.tingcore.users.api.v2.PrivacyPoliciesApi;
import com.tingcore.users.api.v2.TermsAndConditionsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

import static com.tingcore.cdc.ControllerFunctionalTest.userServiceMockServer;

/**
 * @author palmithor
 * @since 2018-05-25
 */
@Configuration
@Profile(SpringProfilesConstant.FUNCTIONAL_TEST)
public class UserServiceRepositoryConfiguration {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ObjectMapper objectMapper;

    @Bean
    @Primary
    TermsAndConditionRepository termsAndConditionRepository() {
        return new TermsAndConditionRepository(objectMapper, getClient().createService(TermsAndConditionsApi.class));
    }

    @Bean
    @Primary
    PrivacyPolicyRepository privacyPolicyRepository() {
        return new PrivacyPolicyRepository(objectMapper, getClient().createService(PrivacyPoliciesApi.class));
    }

    private UserServiceClient getClient() {
        return UserServiceClient.createBuilder()
                .connectionTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .baseUrl(userServiceMockServer.url("/").toString()).build();
    }
}
