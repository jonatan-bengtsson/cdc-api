package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.crm.service.UserService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.MockMvcUtils;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@WebMvcTest(value = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.filter"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.security")
        })
public class UserControllerTest extends ControllerUnitTest {

    @Autowired private HashIdService hashIdService;
    @MockBean private UserService userService;

    @Test
    public void getSelf() throws Exception {
        final User mockResponse = UserDataUtils.createGetUserResponse();
        given(userService.getUserById(authorizedUser.getUser().getId(), true))
                .willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), User.class)).isEqualToComparingFieldByFieldRecursively(mockResponse);
    }

    @Test
    public void getSelfExternalApiError() throws Exception {
        given(userService.getUserById(authorizedUser.getUser().getId(), true))
                .willThrow(new UsersApiException(ErrorResponse.gatewayTimeout().build()));
        mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isGatewayTimeout())
                .andExpect(content().string("{\"statusCode\":504,\"status\":\"Gateway Timeout\"}"));
    }

    @Test
    public void putPrivateCustomerAttributeValues() throws Exception {
        final UpdatePrivateCustomerRequest request = UserDataUtils.createUpdatePrivateCustomerRequest();
        final User response = UserDataUtils.createUpdatePrivateCustomerResponse(request);
        given(userService.putUserAttributeValues(anyLong(), anyLong(), any(UpdatePrivateCustomerRequest.class))).willReturn(response);
        MvcResult result = mockMvc.perform(put("/v1/users/self/values/private-customer")
                .content(mockMvcUtils.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), User.class)).isEqualToComparingFieldByFieldRecursively(response);
    }

    @Test
    public void failPutPrivateCustomerAttributeValuesBadRequest() throws Exception {
        final UpdateBusinessCustomerRequest request = UserDataUtils.createUpdateBusinessCustomerRequest();
        mockMvc.perform(put("/v1/users/self/values/private-customer")
                .content(mockMvcUtils.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putBusinessCustomerAttributeValues() throws Exception {
        final UpdateBusinessCustomerRequest request = UserDataUtils.createUpdateBusinessCustomerRequest();
        final User response = UserDataUtils.createUpdateBusinessCustomerResponse(request);
        given(userService.putUserAttributeValues(anyLong(), anyLong(), any(UpdateBusinessCustomerRequest.class))).willReturn(response);
        MvcResult result = mockMvc.perform(put("/v1/users/self/values/business-customer")
                .content(mockMvcUtils.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), User.class)).isEqualToComparingFieldByFieldRecursively(response);
    }

    @Test
    public void failPutBusinessCustomerAttributeValuesBadRequest() throws Exception {
        final UpdatePrivateCustomerRequest request = UserDataUtils.createUpdatePrivateCustomerRequest();
        mockMvc.perform(put("/v1/users/self/values/business-customer")
                .content(mockMvcUtils.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
















