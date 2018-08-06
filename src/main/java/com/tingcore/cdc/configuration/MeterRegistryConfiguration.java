package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.monitoring.ECSUtils;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeterRegistryConfiguration {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${ECS_CONTAINER_METADATA_FILE:fail.json}") final String metaDataFile,
                                                             final ObjectMapper objectMapper) {
        return registry -> registry.config().commonTags("instance", ECSUtils.getContainerId(metaDataFile, objectMapper));
    }
}
