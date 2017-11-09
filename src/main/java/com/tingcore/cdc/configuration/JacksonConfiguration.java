package com.tingcore.cdc.configuration;

import com.tingcore.cdc.constant.SuppressWarningConstant;
import com.tingcore.cdc.service.HashIdService;
import com.tingcore.cdc.utils.HashIdDeserializer;
import com.tingcore.cdc.utils.HashIdSerializer;
import com.tingcore.cdc.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author palmithor
 * @since 2017-08-31
 */
@SuppressWarnings(SuppressWarningConstant.SPRING_JAVA_AUTOWIRING_INSPECTION)
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(final HashIdService hashIdService) {
        final Jackson2ObjectMapperBuilder objectMapperBuilder = JsonUtils.getObjectMapperBuilder();
        objectMapperBuilder.serializerByType(Long.class, new HashIdSerializer(hashIdService));
        objectMapperBuilder.deserializerByType(Long.class, new HashIdDeserializer(hashIdService));

        return objectMapperBuilder;
    }

}