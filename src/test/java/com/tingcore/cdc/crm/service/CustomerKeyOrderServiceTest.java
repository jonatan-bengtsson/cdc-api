package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.exception.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.repository.CustomerKeyOrderRepository;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.customerkeyorder.client.model.request.CustomerKeyOrderRequest;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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
        when(repository.createOrder(any(CustomerKeyOrderRequest.class)))
                .thenReturn(new ApiResponse<>(CustomerKeyDataUtils.randomCustomerKeyOrderResponse()));

        CustomerKeyOrder result = unitUnderTest.createOrder(USER_ID, ORG_ID, CustomerKeyDataUtils.randomCustomerKeyOrderRequest());

        assertThat(result).hasNoNullFieldsOrPropertiesExcept("updated");
    }

    @Test
    public void failCreateOrder() {
        when(repository.createOrder(any(CustomerKeyOrderRequest.class)))
                .thenReturn(new ApiResponse<>(ErrorResponse.notFound().build()));

        assertThatExceptionOfType(CustomerKeyOrderServiceException.class)
                .isThrownBy(() -> unitUnderTest.createOrder(USER_ID, ORG_ID, CustomerKeyDataUtils.randomCustomerKeyOrderRequest()))
                .withNoCause();
    }

    @Test
    public void findOrdersByUserId() {

        List<CustomerKeyOrder> response = new ArrayList<>();
        response.add(CustomerKeyDataUtils.randomCustomerKeyOrderResponse());
        response.add(CustomerKeyDataUtils.randomCustomerKeyOrderResponse());

        when(repository.findOrdersByUserId(anyLong()))
                .thenReturn(new ApiResponse<>(response));

        List<CustomerKeyOrder> result = unitUnderTest.findOrdersByUserId(USER_ID);

        assertThat(result).hasSize(2);
    }

    @Test
    public void failFindOrdersByUserId() {
        when(repository.findOrdersByUserId(anyLong()))
                .thenReturn(new ApiResponse<>(ErrorResponse.notFound().build()));

        assertThatExceptionOfType(CustomerKeyOrderServiceException.class)
                .isThrownBy(() -> unitUnderTest.findOrdersByUserId(USER_ID))
                .withNoCause();
    }

}
