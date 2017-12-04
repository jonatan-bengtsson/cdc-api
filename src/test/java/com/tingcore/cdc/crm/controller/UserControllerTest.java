package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.crm.service.UserService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.ErrorBodyMatcher;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.UserResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @MockBean private CustomerKeyService customerKeyService;
    @MockBean private UserService userService;
    @MockBean private AuthorizedUser authorizedUser;

    @Before
    public void setUp() {
        final UserResponse authorizedUserMock = mock(UserResponse.class);
        when(authorizedUserMock.getId()).thenReturn(CommonDataUtils.getNextId());
        given(authorizedUser.getUser()).willReturn(authorizedUserMock);
    }

    @Test
    public void findKeysByUserId() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final String hashedId = hashIdService.encode(userId);
        final List<CustomerKeyResponse> customerKeyResponses = Arrays.asList(CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse());
        PageResponse<CustomerKeyResponse> mockResponse = new PageResponse<>(customerKeyResponses);
        given(customerKeyService.findByUserId(userId)).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/{userId}/keys", hashedId))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mockMvcUtils.toJson(mockResponse));
    }

    @Test
    public void failFindKeysByUserIdInvalidType() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final List<CustomerKeyResponse> customerKeyResponses = Arrays.asList(CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse());
        PageResponse<CustomerKeyResponse> mockResponse = new PageResponse<>(customerKeyResponses);
        given(customerKeyService.findByUserId(userId)).willReturn(mockResponse);
        mockMvc.perform(get("/v1/users/{userId}/keys", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getById() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final String hashedId = hashIdService.encode(userId);
        final GetUserResponse mockResponse = UserDataUtils.createGetUserResponse();
        given(userService.getUserById(userId, authorizedUser.getUser().getId(), true)).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/{id}", hashedId)
                .param("includeAttributes", "true"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(mockMvcUtils.toJson(mockResponse));
    }

    @Test
    public void failGetByIdUserNonExisting() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final String hashedId = hashIdService.encode(userId);
        final GetUserResponse mockResponse = UserDataUtils.createGetUserResponse();
        mockMvc.perform(get("/v1/users/{id}", hashedId)
                .param("includeAttributes", "true"))
                .andExpect(status().isNotFound())
                .andExpect(ErrorBodyMatcher.entityNotFoundMatcher("User", hashedId));
    }

    @Test
    public void getSelf() throws Exception {
        final GetUserResponse mockResponse = UserDataUtils.createGetUserResponse();
        given(userService.getUserById(authorizedUser.getUser().getId(), authorizedUser.getUser().getId(), true))
                .willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(mockMvcUtils.toJson(mockResponse));
    }

    @Test
    public void getSelfExternalApiError() throws Exception {
        final GetUserResponse mockResponse = UserDataUtils.createGetUserResponse();
        given(userService.getUserById(authorizedUser.getUser().getId(), authorizedUser.getUser().getId(), true))
                .willThrow(new UsersApiException(ErrorResponse.gatewayTimeout().build()));
        MvcResult result = mockMvc.perform(get("/v1/users/self")
                .param("includeAttributes", "true"))
                .andExpect(status().isGatewayTimeout())
                .andReturn();
    }
}