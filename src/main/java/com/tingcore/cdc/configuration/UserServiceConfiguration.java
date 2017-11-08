package com.tingcore.cdc.configuration;

import com.tingcore.cdc.configuration.IntegrationConfiguration.UserServiceInformation;
import com.tingcore.users.ApiClient;
import com.tingcore.users.api.UsersApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.apache.commons.lang3.Validate.notNull;

@Configuration
public class UserServiceConfiguration {
    @Bean
    public ApiClient userClient(final UserServiceInformation userServiceInformation) {
        final ApiClient userClient = new ApiClient();
        userClient.getAdapterBuilder().baseUrl("https://" + notNull(userServiceInformation).getHostname());
        return userClient;
    }

    @Bean
    public UsersApi usersApi(final ApiClient userClient) {
        return notNull(userClient).createService(UsersApi.class);
    }
}
