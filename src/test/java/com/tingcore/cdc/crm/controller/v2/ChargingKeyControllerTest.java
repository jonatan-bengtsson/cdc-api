package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.service.v2.ChargingKeyService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ChargingKeyController.class)
public class ChargingKeyControllerTest extends ControllerUnitTest {

    @MockBean ChargingKeyService service;

    @Test
    public void activateFail() throws Exception {
        final String keyIdentifier = "key-identifier";

        given(service.activate(anyLong(), anyString()))
                .willThrow(new UsersApiException(ErrorResponse.badRequest().message("Invalid keyIdentifier").build()));

        MockHttpServletRequestBuilder request = post("/v2/charging-keys/activate/" + keyIdentifier)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void activate() throws Exception {
        final String keyIdentifier = "key-identifier";

        given(service.activate(anyLong(), anyString()))
                .willReturn(ChargingKey.createBuilder().keyIdentifier(keyIdentifier).build());

        MockHttpServletRequestBuilder request = post("/v2/charging-keys/activate/" + keyIdentifier)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }
}
