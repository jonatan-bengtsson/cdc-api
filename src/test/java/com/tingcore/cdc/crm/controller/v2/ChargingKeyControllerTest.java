package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.service.v2.ChargingKeyService;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
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

    @Test
    public void update() throws Exception{
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);

        UpdateChargingKeyAppRequest chargingKeyAppRequest = new UpdateChargingKeyAppRequest("new Name");
        ChargingKey response = ChargingKey.createBuilder().keyIdentifier(encodedId).name(chargingKeyAppRequest.getChargingKeyName()).build();

        given(service.updateChargingKey(anyLong(), anyLong(), any()))
                .willReturn(response);

        MockHttpServletRequestBuilder request = put("/v2/charging-keys/" + encodedId)
                .content(mockMvcUtils.toJson(chargingKeyAppRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }


    @Test
    public void updateFail() throws Exception{
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);
        MockHttpServletRequestBuilder request = put("/v2/charging-keys/" + encodedId)
                .content("hello world")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFailNameTooLong() throws Exception{
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);
        UpdateChargingKeyAppRequest chargingKeyAppRequest = new UpdateChargingKeyAppRequest(CommonDataUtils.nextZeroPaddedId(51));
        MockHttpServletRequestBuilder request = put("/v2/charging-keys/" + encodedId)
                .content(mockMvcUtils.toJson(chargingKeyAppRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
