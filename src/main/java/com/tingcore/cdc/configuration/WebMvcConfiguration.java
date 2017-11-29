package com.tingcore.cdc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    public static final String AUTHORIZED_USER = "authorizedUser";
    static final String HTTP_METHOD_GET = "GET";
    static final String HTTP_METHOD_POST = "POST";
    static final String HTTP_METHOD_PUT = "PUT";
    static final String HTTP_METHOD_DELETE = "DELETE";
    static final String HTTP_METHOD_PATCH = "PATCH";
    private List<String> allowedOrigins = new ArrayList<>();


    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AuthorizedUser authorizedUser() {
        return new AuthorizedUser();
    }

    /**
     * Override needed to enable matrix parameters in endpoints.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(new String[allowedOrigins.size()]))
                .allowedMethods(HTTP_METHOD_GET, HTTP_METHOD_POST, HTTP_METHOD_PUT, HTTP_METHOD_DELETE, HTTP_METHOD_PATCH);
        ;
    }


    @Value("#{'${app.allowed-origins}'.split(',')}")
    public void set(final List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}