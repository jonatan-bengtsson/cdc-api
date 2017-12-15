package com.tingcore.cdc.crm.controller;


import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.CustomerKeyResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
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


    // TODO rewrite when customer keys have been finalized
    @Test
    public void getCustomerKeys() throws Exception {
        final PageResponseCustomerKeyResponse pageResponseCustomerKeyResponse = new PageResponseCustomerKeyResponse();
        final CustomerKeyResponse o = new CustomerKeyResponse();
        o.setId(CommonDataUtils.getNextId());
        pageResponseCustomerKeyResponse.setContent(Collections.singletonList(o));
        given(customerKeyService.findByUserId(authorizedUser.getUser().getId()))
                .willReturn(pageResponseCustomerKeyResponse);
        MvcResult result = mockMvc.perform(get("/v1/customer-keys"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), PageResponseCustomerKeyResponse.class))
                .isEqualToComparingFieldByFieldRecursively(pageResponseCustomerKeyResponse);
    }
}