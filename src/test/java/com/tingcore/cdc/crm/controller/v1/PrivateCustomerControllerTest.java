package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.crm.service.v1.UserService;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import org.junit.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.filter"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.security")
        })
public class PrivateCustomerControllerTest extends ControllerUnitTest {

    @MockBean
    private UserService userService;

    @Test
    public void putPrivateCustomerAttributeValues() throws Exception {
        final UpdatePrivateCustomerRequest request = UserDataUtils.createUpdatePrivateCustomerRequest();
        final User response = UserDataUtils.createUpdatePrivateCustomerResponse(request);
        given(userService.putUserAttributeValues(anyLong(), any())).willReturn(response);
        MvcResult result = mockMvc.perform(put("/v1/private-customers/self")
                .content(mockMvcUtils.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mockMvcUtils.fromJson(result.getResponse().getContentAsString(), User.class)).isEqualToComparingFieldByFieldRecursively(response);
    }

    @Test
    public void failPutPrivateCustomerAttributeValuesBadRequest() throws Exception {
        mockMvc.perform(put("/v1/private-customers/self")
                .content("hello world")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
