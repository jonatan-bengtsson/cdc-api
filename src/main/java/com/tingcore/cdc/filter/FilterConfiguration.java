package com.tingcore.cdc.filter;

import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.commons.api.service.HashIdService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final HashIdService hashIdService;
    private final FilterUtils filterUtils;

    @Autowired
    public FilterConfiguration(final HashIdService hashIdService, final FilterUtils filterUtils) {
        this.hashIdService = hashIdService;
        this.filterUtils = filterUtils;
    }

    @Bean
    AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(hashIdService, filterUtils);
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
}
