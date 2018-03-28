package com.tingcore.cdc.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.cdc.crm.service.CustomerKeyOrderService;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.commons.api.crm.model.Organization;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CustomerKeyOrderController.class)
public class CustomerKeyOrderControllerTest extends ControllerUnitTest {

    @MockBean private CustomerKeyOrderService service;

    @Test
    public void createCustomerKeyOrder() throws Exception {

        CustomerKeyOrder mockResponse = CustomerKeyDataUtils.randomCustomerKeyOrderResponse();

        given(authorizedUser.getOrganization()).willReturn(Organization.createBuilder().id(1L).build());
        given(service.createOrder(anyLong(), anyLong(), any(CustomerKeyOrderRequest.class))).willReturn(mockResponse);

        MockHttpServletRequestBuilder request = post("/v1/customer-key-orders")
                .content(mockMvcUtils.toJson(CustomerKeyDataUtils.randomCustomerKeyOrderRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), CustomerKeyOrder.class))
                .isEqualToComparingFieldByFieldRecursively(mockResponse);

    }

    @Test
    public void failCreateCustomerKeyOrderNoOrg() throws Exception {

        CustomerKeyOrder mockResponse = CustomerKeyDataUtils.randomCustomerKeyOrderResponse();

        given(service.createOrder(anyLong(), anyLong(), any(CustomerKeyOrderRequest.class))).willReturn(mockResponse);

        MockHttpServletRequestBuilder request = post("/v1/customer-key-orders")
                .content(mockMvcUtils.toJson(CustomerKeyDataUtils.randomCustomerKeyOrderRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), ErrorResponse.class))
                .hasFieldOrPropertyWithValue("message","Current user has no organization");

    }

    @Test
    public void failCreateCustomerKeyOrder() throws Exception {

        CustomerKeyOrder mockResponse = CustomerKeyDataUtils.randomCustomerKeyOrderResponse();

        given(service.createOrder(anyLong(), anyLong(), any(CustomerKeyOrderRequest.class))).willReturn(mockResponse);

        MockHttpServletRequestBuilder request = post("/v1/customer-key-orders")
                .content(mockMvcUtils.toJson(CustomerKeyDataUtils.randomInvalidCustomerKeyOrderRequest()))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), ErrorResponse.class))
                .hasFieldOrPropertyWithValue("message","Validation failed")
                .extracting("fieldViolations").hasSize(1);

    }

    @Test
    public void getUserCustomerKeyOrders() throws Exception {

        List<CustomerKeyOrder> response = new ArrayList<>();
        response.add(CustomerKeyDataUtils.randomCustomerKeyOrderResponse());
        response.add(CustomerKeyDataUtils.randomCustomerKeyOrderResponse());
        given(service.findOrdersByUserId(anyLong())).willReturn(response);

        MockHttpServletRequestBuilder request = get("/v1/customer-key-orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), new TypeReference<PageResponse<CustomerKeyOrder>>() {}))
                .isEqualToComparingFieldByFieldRecursively(new PageResponse<>(response));

    }


}
