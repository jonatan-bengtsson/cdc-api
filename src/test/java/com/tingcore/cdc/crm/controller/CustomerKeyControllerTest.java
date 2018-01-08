package com.tingcore.cdc.crm.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.ErrorBodyMatcher;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@WebMvcTest(value = CustomerKeyController.class)
public class CustomerKeyControllerTest extends ControllerUnitTest {

    @MockBean private CustomerKeyService customerKeyService;


    @Test
    public void getCustomerKeys() throws Exception {
        final PageResponse<CustomerKey> mockResponse = new PageResponse<>(
                newArrayList(CustomerKeyDataUtils.randomCustomerKey().build(), CustomerKeyDataUtils.randomCustomerKey().build()));
        given(customerKeyService.findByUserId(authorizedUser.getUser().getId())).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/customer-keys"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.pageFromJson(result.getResponse().getContentAsString(), new TypeReference<PageResponse<CustomerKey>>() {
        })).isEqualToComparingFieldByFieldRecursively(mockResponse);
    }


    @Test
    public void failGetCustomerKeyByIdExternalGatewayTimeout() throws Exception {
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);
        given(customerKeyService.findByCustomerKeyId(authorizedUser.getUser().getId(), id)).willThrow(new UsersApiException(ErrorResponse.gatewayTimeout().build()));
        mockMvc.perform(get("/v1/customer-keys/{id}", encodedId))
                .andExpect(status().isGatewayTimeout())
                .andExpect(content().string("{\"statusCode\":504,\"status\":\"Gateway Timeout\"}"));
    }

    @Test
    public void getCustomerKeyById() throws Exception {
        final CustomerKey mockResponse = CustomerKeyDataUtils.randomCustomerKey().build();
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);
        given(customerKeyService.findByCustomerKeyId(authorizedUser.getUser().getId(), id)).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/customer-keys/{id}", encodedId))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), CustomerKey.class)).isEqualToComparingFieldByFieldRecursively(mockResponse);
    }

    @Test
    public void failGetCustomerKeyByIdInvalidId() throws Exception {
        mockMvc.perform(get("/v1/customer-keys/{id}", "invalid"))
                .andExpect(status().isNotFound())
                .andExpect(ErrorBodyMatcher.entityNotFoundMatcher("CustomerKey", "invalid"));
    }

    @Test
    public void createCustomerKeySuccess() throws Exception {
        final CustomerKeyPostRequest request = CustomerKeyDataUtils.randomRequestAllValid().build();
        final CustomerKey response = CustomerKeyDataUtils.randomCustomerKey().build();
        given(customerKeyService.create(anyLong(), any(CustomerKeyPostRequest.class))).willReturn(response);

        MvcResult result = mockMvc.perform(
                post("/v1/customer-keys", CommonDataUtils.getNextId())
                        .content(mockMvcUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), CustomerKey.class))
                .isEqualToComparingFieldByFieldRecursively(response);
    }

    @Test
    public void failCreateCustomerKey() throws Exception {
        final Long id = CommonDataUtils.getNextId();
        final CustomerKeyPostRequest request = CustomerKeyDataUtils.randomRequestAllValid().build();
        given(customerKeyService.create(anyLong(), any(CustomerKeyPostRequest.class)))
                .willThrow(new EntityNotFoundException(User.class.getSimpleName()));

        mockMvc.perform(
                post("/v1/customer-keys", CommonDataUtils.getNextId())
                        .content(mockMvcUtils.toJson(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(ErrorBodyMatcher.entityNotFoundMatcher("User"));
    }
}