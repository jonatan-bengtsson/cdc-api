package com.tingcore.cdc.security;

import com.tingcore.cdc.constant.SpringProfilesConstant;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({
        SecurityConfiguration.SecurityProperties.class
})
@Profile({SpringProfilesConstant.TEST, SpringProfilesConstant.STAGE, SpringProfilesConstant.PROD})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ApplicationContext applicationContext;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public SecurityConfiguration(final ApplicationContext applicationContext, final AuthenticationProvider authenticationProvider, final AuthenticationSuccessHandler authenticationSuccessHandler, final AuthenticationFailureHandler authenticationFailureHandler) {
        this.applicationContext = applicationContext;
        this.authenticationProvider = authenticationProvider;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(AnyRequestMatcher.INSTANCE);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setAllowSessionCreation(false);
        authenticationFilter.setApplicationEventPublisher(applicationContext);
        authenticationFilter.setAuthenticationDetailsSource(new AuthenticationMetadataSource());
        return authenticationFilter;
    }

    @Bean
    public FilterRegistrationBean registerAuthenticationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(authenticationFilter());
        registrationBean.setOrder(1);
        registrationBean.setUrlPatterns(Collections.singletonList("/v1/*"));
        return registrationBean;
    }

    @Override
    protected void configure(final HttpSecurity security) throws Exception {
        security.csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.headers().cacheControl();
    }

    @Configuration
    @ConfigurationProperties(prefix = "security")
    public static class SecurityProperties {
        @NotBlank
        private String header;

        String header() {
            return header;
        }

        public void setHeader(final String header) {
            this.header = header;
        }
    }
}
