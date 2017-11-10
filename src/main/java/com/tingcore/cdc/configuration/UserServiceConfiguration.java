package com.tingcore.cdc.configuration;

import com.tingcore.cdc.configuration.IntegrationConfiguration.UserServiceInformation;
import com.tingcore.users.ApiClient;
import com.tingcore.users.api.UsersApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {
    @Bean
    public ApiClient apiClient(final UserServiceInformation userServiceInformation) {
        final ApiClient apiClient = new ApiClient();
        apiClient.getAdapterBuilder().baseUrl("https://" + userServiceInformation.getHostname());
        return apiClient;
    }

    @Bean
    public UsersApi usersApi(final ApiClient apiClient) {
        return apiClient.createService(UsersApi.class);
    }
}
