package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.PageResponse;
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


    @Test
    public void findByUserId() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final String hashedId = hashIdService.encode(userId);
        final List<CustomerKeyResponse> customerKeys = Arrays.asList(CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse());
        PageResponse<CustomerKeyResponse> mockResponse = new PageResponse<>(customerKeys);
        given(customerKeyService.findByUserId(userId)).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/users/{userId}/keys", hashedId))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mockMvcUtils.toJson(mockResponse));
    }

    @Test
    public void failFindByUserIdInvalidType() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final List<CustomerKeyResponse> customerKeys = Arrays.asList(CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse(), CustomerKeyDataUtils.customerKeyResponse());
        PageResponse<CustomerKeyResponse> mockResponse = new PageResponse<>(customerKeys);
        given(customerKeyService.findByUserId(userId)).willReturn(mockResponse);
        mockMvc.perform(get("/v1/users/{userId}/keys", userId))
                .andExpect(status().isNotFound());
    }
}