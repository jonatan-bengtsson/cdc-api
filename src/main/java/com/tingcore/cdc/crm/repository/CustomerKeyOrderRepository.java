package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.customerkeyorder.client.CustomerKeyOrderServiceApi;
import com.tingcore.customerkeyorder.client.model.request.CustomerKeyOrderRequest;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerKeyOrderRepository extends AbstractUserServiceRepository {

    private final CustomerKeyOrderServiceApi customerKeyOrderServiceApi;

    public CustomerKeyOrderRepository(final ObjectMapper objectMapper, final CustomerKeyOrderServiceApi customerKeyOrderServiceApi) {
        super(objectMapper);
        this.customerKeyOrderServiceApi = customerKeyOrderServiceApi;
    }

    public ApiResponse<CustomerKeyOrder> createOrder(final CustomerKeyOrderRequest request) {
        return execute(customerKeyOrderServiceApi.createOrder(request));
    }

    public ApiResponse<List<CustomerKeyOrder>> findOrdersByUserId(final Long userId) {
        return execute(customerKeyOrderServiceApi.findOrdersByUserId(userId));
    }
}
