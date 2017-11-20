package com.tingcore.cdc.crm.repository;

import com.tingcore.cdc.configuration.CacheConfiguration;
import com.tingcore.users.api.AttributesApi;
import com.tingcore.users.model.AttributeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author palmithor
 * @since 2017-11-10
 */
@Repository
public class AttributeRepository {

    private static final Logger logger = LoggerFactory.getLogger(AttributeRepository.class);

    private final AttributesApi attributesApi;

    public AttributeRepository(final AttributesApi attributesApi) {
        this.attributesApi = attributesApi;
    }


    @Cacheable(CacheConfiguration.USER_SERVICE_ATTRIBUTES)
    public List<AttributeResponse> findAll() {
        try {
            return attributesApi.getAttributesUsingGET().get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("Unable to get attributes", e);
            return new ArrayList<>();
        }
    }

    @CacheEvict(CacheConfiguration.USER_SERVICE_ATTRIBUTES)
    public void resetCache() {
        findAll();
    }
}
