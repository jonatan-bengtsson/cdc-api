package com.tingcore.cdc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.util.UrlPathHelper;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfigurations extends WebMvcConfigurerAdapter {

    private List<String> allowedOrigins = new ArrayList<>();


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
        //registry.addMapping("/**")
        //        .allowedOrigins(allowedOrigins.toArray(new String[allowedOrigins.size()]));
    }


    @Value("#{'${app.allowed-origins}'.split(',')}")
    public void set(final List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}