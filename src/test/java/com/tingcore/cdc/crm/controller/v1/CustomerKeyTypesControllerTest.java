package com.tingcore.cdc.crm.controller.v1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.controller.v1.CustomerKeyTypesController;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.service.v1.CustomerKeyService;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author palmithor
 * @since 2018-01-11
 */
@WebMvcTest(value = CustomerKeyTypesController.class)
public class CustomerKeyTypesControllerTest extends ControllerUnitTest {

    @MockBean private CustomerKeyService customerKeyService;

    @Test
    public void getCustomerKeyTypes() throws Exception {
        final List<CustomerKeyType> mockResponse = newArrayList(CustomerKeyDataUtils.randomCustomerKeyType().build(),
                CustomerKeyDataUtils.randomCustomerKeyType().build());
        given(customerKeyService.getCustomerKeyTypes()).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/customer-key-types"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), new TypeReference<List<CustomerKeyType>>() {
        })).hasSameElementsAs(mockResponse);
    }
}