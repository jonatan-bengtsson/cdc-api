package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.commons.monitoring.EcsMeterRegistryConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(SpringProfilesConstant.MONITORING)
public class MeterRegistryConfiguration extends EcsMeterRegistryConfiguration {

    public MeterRegistryConfiguration(
            @Value("${ECS_CONTAINER_METADATA_FILE:fail.json}") String metaDataFile,
            ObjectMapper objectMapper) {

        super(metaDataFile, objectMapper);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return this.getGlobalMetricTagsCustomizer();
    }

}
