package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.service.UserService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        given(userService.getUserById(authorizedUser.getId(), true))
                .willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), User.class)).isEqualToComparingFieldByFieldRecursively(mockResponse);
    }

    @Test
    public void getSelfExternalApiError() throws Exception {
        given(userService.getUserById(authorizedUser.getId(), true))
                .willThrow(new UsersApiException(ErrorResponse.gatewayTimeout().build()));
        mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isGatewayTimeout())
                .andExpect(content().string("{\"statusCode\":504,\"status\":\"Gateway Timeout\"}"));
    }
}