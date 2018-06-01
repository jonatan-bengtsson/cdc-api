package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author palmithor
 * @since 2017-12-15
 */
public abstract class AbstractUserServiceRepository extends AbstractApiRepository {


    private Integer defaultTimeOut;

    public AbstractUserServiceRepository(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeOut;
    }
}
