package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.repository.CustomerKeyOrderRepository;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.customerkeyorder.client.model.request.OrderRequest;
import com.tingcore.customerkeyorder.client.model.response.Order;
import org.springframework.stereotype.Service;

@Service
public class CustomerKeyOrderService {

    private final CustomerKeyOrderRepository repository;
    private static final Long CUSTOMER_KEY_RFID_TAG_TYPE_ID = 1L;

    public CustomerKeyOrderService(final CustomerKeyOrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder(Long userId, CustomerKeyOrderRequest request) {
        ApiResponse<Order> orderResponse = repository.createOrder(map(userId, request));
        return orderResponse
                .getResponseOptional()
                .orElseThrow(() -> new CustomerKeyOrderServiceException(orderResponse.getError()));
    }

    private OrderRequest map(Long userId, CustomerKeyOrderRequest restRequest) {
        return new OrderRequest(
                userId,
                restRequest.getAddress(),
                restRequest.getQuantity(),
                CUSTOMER_KEY_RFID_TAG_TYPE_ID);
    }

}
