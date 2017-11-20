package com.tingcore.cdc.filter;

import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.commons.api.filter.CognitoParamsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

/**
 * @author palmithor
 * @since 2017-11-14
 */
@Configuration
@Profile({
        SpringProfilesConstant.TEST,
        SpringProfilesConstant.STAGE,
        SpringProfilesConstant.PROD,
        SpringProfilesConstant.DEV,
        SpringProfilesConstant.FILTER_TEST,
        SpringProfilesConstant.FUNCTIONAL_TEST
})
public class FilterConfiguration {

    private final FilterUtils filterUtils;
    private final UserRepository userRepository;
    private String cognitoUserIdHeaderName;
    private String cognitoAuthProviderHeaderName;

    @Autowired
    public FilterConfiguration(final UserRepository userRepository, final FilterUtils filterUtils) {
        this.userRepository = userRepository;
        this.filterUtils = filterUtils;
    }

    @Bean
    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(userRepository, filterUtils, cognitoAuthProviderHeaderName, cognitoUserIdHeaderName);
    }

    /**
     * Registration of the filter which sets the cognito user id.
     * No verification is done in this filter, it only extracts the values from the cognitoAuthProviderHeaderName and
     * stores it in cognitoUserIdHeaderName. Thus it is okay to apply it to all resources
     */
    @Bean
    public FilterRegistrationBean registerCognitoParamsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new CognitoParamsFilter(cognitoAuthProviderHeaderName, cognitoUserIdHeaderName));
        registrationBean.setOrder(1);
        registrationBean.setUrlPatterns(Collections.singletonList("*"));

        return registrationBean;
    }

    /**
     * Registration of the filter which verifies with the user service that the authenticated user exists.
     * The filter initializes the AuthorizedUser request scoped bean
     */
    @Bean
    public FilterRegistrationBean registerAuthorizationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(authorizationFilter());
        registrationBean.setOrder(2);
        registrationBean.setUrlPatterns(Collections.singletonList("/v1/*"));

        return registrationBean;
    }

    @Value("${app.aws.headers.cognito-auth-provider}")
    public void setCognitoAuthProviderHeaderName(final String cognitoAuthProviderHeaderName) {
        this.cognitoAuthProviderHeaderName = cognitoAuthProviderHeaderName;
    }

    @Value("${app.aws.headers.cognito-user-id}")
    public void setCognitoUserIdHeaderName(final String cognitoUserIdHeaderName) {
        this.cognitoUserIdHeaderName = cognitoUserIdHeaderName;
    }
}
