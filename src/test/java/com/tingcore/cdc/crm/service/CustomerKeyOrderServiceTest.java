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

    private final Long USER_ID = 1L;
    private final Long ORG_ID = 1L;

    @Before
    public void setUp() {
        unitUnderTest = new CustomerKeyOrderService(repository);
    }

    @Test
    public void createOrder() {
        when(repository.createOrder(any(OrderRequest.class)))
                .thenReturn(new ApiResponse<>(CustomerKeyDataUtils.randomCustomerKeyOrderResponse()));


        Order result = unitUnderTest.createOrder(USER_ID, ORG_ID, CustomerKeyDataUtils.randomCustomerKeyOrderRequest());

        assertThat(result).hasNoNullFieldsOrPropertiesExcept("updated");
    }

    @Test
    public void failCreateOrder() {
        when(repository.createOrder(any(OrderRequest.class)))
                .thenReturn(new ApiResponse<>(ErrorResponse.notFound().build()));


        assertThatExceptionOfType(CustomerKeyOrderServiceException.class)
                .isThrownBy(() -> unitUnderTest.createOrder(USER_ID, ORG_ID, CustomerKeyDataUtils.randomCustomerKeyOrderRequest()))
                .withNoCause();
    }

}
