package com.tingcore.cdc.service;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author palmithor
 * @since 2017-04-27.
 */
@Service
public class MessageByLocaleService {

    private final MessageSource messageSource;

    @Autowired
    public MessageByLocaleService(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    /**
     * Get message by the environments locale
     */
    public String getMessage(final String key) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Get message with the provided locale
     */
    public String getMessage(final String key, final Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }
}
