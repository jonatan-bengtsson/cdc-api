package com.tingcore.cdc.crm.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.response.CustomerKey;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.commons.rest.PageResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@WebMvcTest(value = CustomerKeyController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.filter"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.security")
        })
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
}