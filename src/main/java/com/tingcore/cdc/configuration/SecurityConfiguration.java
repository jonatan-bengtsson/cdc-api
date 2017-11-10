package com.tingcore.cdc.configuration;

import com.tingcore.cdc.security.AuthenticationFailureHandler;
import com.tingcore.cdc.security.AuthenticationFilter;
import com.tingcore.cdc.security.AuthenticationMetadataSource;
import com.tingcore.cdc.security.AuthenticationSuccessHandler;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({
        SecurityConfiguration.SecurityProperties.class
})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(AnyRequestMatcher.INSTANCE);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        authenticationFilter.setAllowSessionCreation(false);
        authenticationFilter.setApplicationEventPublisher(applicationContext);
        authenticationFilter.setAuthenticationDetailsSource(new AuthenticationMetadataSource());
        return authenticationFilter;
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

        public String header() {
            return header;
        }

        public void setHeader(final String header) {
            this.header = header;
        }
    }
}
