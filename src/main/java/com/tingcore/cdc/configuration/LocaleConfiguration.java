package com.tingcore.cdc.configuration;

import com.tingcore.commons.core.constant.SuppressWarningConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

;

/**
 * @author palmithor
 * @since 2017-05-09.
 */
@Configuration
@SuppressWarnings(SuppressWarningConstant.SPRING_FACET_CODE_INSPECTION)
public class LocaleConfiguration {

    private static final String RESOURCE_BUNDLE_BASE_NAME = "i18n/messages";

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US); // Set default Locale as US
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames(RESOURCE_BUNDLE_BASE_NAME);  // name of the resource bundle
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
