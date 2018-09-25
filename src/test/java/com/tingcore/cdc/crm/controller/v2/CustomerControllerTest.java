package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.v2.CustomerService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.users.model.v2.request.PrivateCustomerUpdateRequest;
import com.tingcore.users.model.v2.response.PrivateCustomer;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CustomerController.class)
public class CustomerControllerTest extends ControllerUnitTest {

    @MockBean
    CustomerService service;

    @Test
    public void getById() throws Exception {
        given(service.findById(anyLong()))
                .willReturn(PrivateCustomer.createBuilder().firstName("customer").build());
        mockMvc.perform(get("/v2/customers/self")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
}

    @Test
    public void failGetById() throws Exception{
        given(service.findById(anyLong())).willThrow(new EntityNotFoundException());
        mockMvc.perform(get("/v2/customers/self")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCustomer() throws Exception{
        given(service.update(anyLong(), any()))
                .willReturn(PrivateCustomer.createBuilder().firstName("customer").build());

        PrivateCustomerUpdateRequest request = PrivateCustomerUpdateRequest.createBuilder().version(2L).build();

        mockMvc.perform(put("/v2/customers/self")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockMvcUtils.toJson(request)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void failUpdateCustomer() throws Exception{
        given(service.update(anyLong(), any()))
                .willThrow(new EntityNotFoundException());

        PrivateCustomerUpdateRequest request = PrivateCustomerUpdateRequest.createBuilder().version(2L).build();

        mockMvc.perform(put("/v2/customers/self")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockMvcUtils.toJson(request)))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
