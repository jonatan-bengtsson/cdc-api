package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.charging.operations.api.OperationsApi;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class OperationsRepository extends AbstractApiRepository {
    private final OperationsApi operationsApi;

    @Override
    public Integer getTimeout() {
        return 120;
    }

    @Autowired
    public OperationsRepository(final ObjectMapper objectMapper, final OperationsApi operationsApi) {
        super(objectMapper);
        this.operationsApi = operationsApi;
    }

    public OperationsApi getOperationsApi() {
        return operationsApi;
    }
}
