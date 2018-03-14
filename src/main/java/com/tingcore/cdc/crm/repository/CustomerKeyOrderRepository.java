package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.customerkeyorder.client.CustomerKeyOrderServiceApi;
import com.tingcore.customerkeyorder.client.model.request.OrderRequest;
import com.tingcore.customerkeyorder.client.model.response.Order;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerKeyOrderRepository extends AbstractUserServiceRepository {

    private final CustomerKeyOrderServiceApi customerKeyOrderServiceApi;

    public CustomerKeyOrderRepository(final ObjectMapper objectMapper, CustomerKeyOrderServiceApi customerKeyOrderServiceApi) {
        super(objectMapper);
        this.customerKeyOrderServiceApi = customerKeyOrderServiceApi;
    }

    public ApiResponse<Order> createOrder(final OrderRequest request) {
        return execute(customerKeyOrderServiceApi.createOrder(request));
    }
}
