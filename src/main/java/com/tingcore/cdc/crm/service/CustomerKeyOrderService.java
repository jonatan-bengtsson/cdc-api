package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.repository.CustomerKeyOrderRepository;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerKeyOrderService {

    private final CustomerKeyOrderRepository repository;
    private static final Long CUSTOMER_KEY_RFID_TAG_TYPE_ID = 1L;

    public CustomerKeyOrderService(final CustomerKeyOrderRepository repository) {
        this.repository = repository;
    }

    public CustomerKeyOrder createOrder(Long userId, Long orgId, CustomerKeyOrderRequest request) {
        ApiResponse<CustomerKeyOrder> orderResponse = repository.createOrder(map(userId, orgId, request));
        return orderResponse
                .getResponseOptional()
                .orElseThrow(() -> new CustomerKeyOrderServiceException(orderResponse.getError()));
    }

    public List<CustomerKeyOrder> findOrdersByUserId(Long userId) {
        ApiResponse<List<CustomerKeyOrder>> response = repository.findOrdersByUserId(userId);
        return response
                .getResponseOptional()
                .orElseThrow(() -> new CustomerKeyOrderServiceException(response.getError()));
    }

    private com.tingcore.customerkeyorder.client.model.request.CustomerKeyOrderRequest map(Long userId, Long orgId, CustomerKeyOrderRequest restRequest) {
        return new com.tingcore.customerkeyorder.client.model.request.CustomerKeyOrderRequest.Builder()
                .withUserId(userId)
                .withOrganizationId(orgId)
                .withAddress(restRequest.getAddress())
                .withQuantity(restRequest.getQuantity())
                .withCustomerKeyType(CUSTOMER_KEY_RFID_TAG_TYPE_ID)
                .build();
    }

}
