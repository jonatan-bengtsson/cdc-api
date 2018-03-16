package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.repository.CustomerKeyOrderRepository;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.customerkeyorder.client.model.request.OrderRequest;
import com.tingcore.customerkeyorder.client.model.response.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CustomerKeyOrderServiceTest {

    @MockBean private CustomerKeyOrderRepository repository;

    private CustomerKeyOrderService unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new CustomerKeyOrderService(repository);
    }

    @Test
    public void createOrder() {
        when(repository.createOrder(any(OrderRequest.class)))
                .thenReturn(new ApiResponse<>(CustomerKeyDataUtils.randomCustomerKeyOrderResponse()));


        Order result = unitUnderTest.createOrder(1L, CustomerKeyDataUtils.randomCustomerKeyOrderRequest());

        assertThat(result).isNotNull();
    }

    @Test
    public void failCreateOrder() {
        when(repository.createOrder(any(OrderRequest.class)))
                .thenReturn(new ApiResponse<>(ErrorResponse.notFound().build()));


        assertThatExceptionOfType(CustomerKeyOrderServiceException.class)
                .isThrownBy(() -> unitUnderTest.createOrder(1L, CustomerKeyDataUtils.randomCustomerKeyOrderRequest()))
                .withNoCause();
    }

}
