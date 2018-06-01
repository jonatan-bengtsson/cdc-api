package com.tingcore.cdc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.commons.hash.HashIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.util.Arrays.asList;

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

    private final HashIdService hashIdService;
    private final FilterUtils filterUtils;
    private final ObjectMapper objectMapper;

    @Autowired
    public FilterConfiguration(final HashIdService hashIdService, final FilterUtils filterUtils, final ObjectMapper objectMapper) {
        this.hashIdService = hashIdService;
        this.filterUtils = filterUtils;
        this.objectMapper = objectMapper;
    }

    @Bean
    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(hashIdService, filterUtils, objectMapper);
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
        registrationBean.setUrlPatterns(asList(
                "/v1/*",
                "/v2/*"));

        return registrationBean;
    }
}
