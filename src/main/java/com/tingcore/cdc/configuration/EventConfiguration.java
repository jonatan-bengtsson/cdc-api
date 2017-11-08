package com.tingcore.cdc.configuration;

import com.tingcore.cdc.Application;
import com.tingcore.library.eventprocessing.EventManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {
    @Bean
    public EventManager eventManager() {
        return EventManager.builder(Application.class.getPackage().getName())
                .build();
    }
}
