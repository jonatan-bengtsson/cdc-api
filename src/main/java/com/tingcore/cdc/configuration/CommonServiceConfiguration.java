package com.tingcore.cdc.configuration;

import com.tingcore.commons.api.service.CustomServiceModelToSwagger2Mapper;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.api.utils.HashIdDeserializer;
import com.tingcore.commons.api.utils.HashIdSerializer;
import com.tingcore.commons.api.utils.PaginationConverterService;
import com.tingcore.commons.utils.JsonUtils;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

@Configuration
public class CommonServiceConfiguration {


    // MAKE SURE that if this salt is changed (moved to another service, or different between environments)
    // to change it in cd-lambda pre_token_generation script
    private static final String SALT = "6d01516d-27a8-427d-ade2-0284efcd2c53";
    private static final int HASH_LENGTH = 10;


    @Bean
    public Hashids hashids() {
        return new Hashids(SALT, HASH_LENGTH);
    }

    @Bean
    public HashIdService hashIdService() {
        return new HashIdService(hashids());
    }

    @Bean
    public PaginationConverterService paginationConverterService() {
        return new PaginationConverterService(hashIdService());
    }

    @Bean
    @Primary
    public ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper() {
        return new CustomServiceModelToSwagger2Mapper(hashIdService());
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        final Jackson2ObjectMapperBuilder objectMapperBuilder = JsonUtils.getObjectMapperBuilder();
        objectMapperBuilder.serializerByType(Long.class, new HashIdSerializer(hashIdService()));
        objectMapperBuilder.deserializerByType(Long.class, new HashIdDeserializer(hashIdService()));

        return objectMapperBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
