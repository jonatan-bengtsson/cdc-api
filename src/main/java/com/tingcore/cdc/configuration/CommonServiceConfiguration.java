package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.tingcore.commons.api.service.CustomServiceModelToSwagger2Mapper;
import com.tingcore.commons.hash.HashIdDeserializer;
import com.tingcore.commons.hash.HashIdSerializer;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.service.PagingConverterService;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    public PagingConverterService pagingConverterService() {
        return new PagingConverterService(hashIdService());
    }

    @Bean
    @Primary
    public ServiceModelToSwagger2Mapper serviceModelToSwagger2Mapper() {
        return new CustomServiceModelToSwagger2Mapper(hashIdService());
    }

    @Bean
    public ObjectMapper objectMapper(final HashIdService hashIdService) {
        final ObjectMapper objectMapper = com.tingcore.commons.core.utils.JsonUtils.getObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new HashIdSerializer(hashIdService));
        module.addDeserializer(Long.class, new HashIdDeserializer(hashIdService));
        objectMapper.registerModule(module);
        objectMapper.registerModule(new Jdk8Module());

        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
