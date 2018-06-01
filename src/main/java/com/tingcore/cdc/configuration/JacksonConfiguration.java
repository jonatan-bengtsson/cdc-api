package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.commons.core.utils.JsonUtils;
import com.tingcore.commons.hash.HashIdDeserializer;
import com.tingcore.commons.hash.HashIdSerializer;
import com.tingcore.commons.hash.HashIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author palmithor
 * @since 2017-08-31
 */
@SuppressWarnings(SuppressWarningConstant.SPRING_JAVA_AUTOWIRING_INSPECTION)
@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper(final HashIdService hashIdService) {
        final ObjectMapper objectMapper = JsonUtils.getObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new HashIdSerializer(hashIdService));
        module.addDeserializer(Long.class, new HashIdDeserializer(hashIdService));
        objectMapper.registerModule(module);

        return objectMapper;
    }

}
