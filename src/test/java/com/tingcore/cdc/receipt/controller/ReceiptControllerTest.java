package com.tingcore.cdc.receipt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.payments.controller.ReceiptController;
import com.tingcore.cdc.payments.service.v2.ReceiptServiceImpl;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.payments.cpo.model.ApiReceipt;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ReceiptController.class)
public class ReceiptControllerTest extends ControllerUnitTest {

    @MockBean
    private ReceiptServiceImpl service;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getReceiptUrl() throws Exception {
        final Long sessionId = 11L;
        final String hashId = hashIdService.encode(sessionId);
        final String mockUrl = "receipt-url";

        ApiReceipt apiReceipt = new ApiReceipt();
        apiReceipt.setUrl(mockUrl);

        when(service.getReceipt(sessionId)).thenReturn(apiReceipt);

        MvcResult mvcResult = mockMvc.perform(get("/v1/receipts/sessions/" + hashId))
                .andExpect(status().isOk())
                .andReturn();

        ApiReceipt receipt = mapper.readValue(mvcResult.getResponse().getContentAsString(), ApiReceipt.class);
        assertThat(receipt.getUrl()).isEqualTo(mockUrl);
    }

    @Test
    public void failGetReceiptUrlNotFound() throws Exception {
        final Long sessionId = 11L;
        final String hashId = hashIdService.encode(sessionId);
        when(service.getReceipt(anyLong())).thenThrow(new EntityNotFoundException("Receipt"));
        MvcResult mvcResult = mockMvc.perform(get("/v1/receipts/sessions/" + hashId))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponse errorResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(errorResponse.getMessage()).isEqualTo("Entity not found");
    }

    @Test
    public void failGetReceiptUrlDecodeError() throws Exception {
        final String id = "noHashId";
        MvcResult mvcResult = mockMvc.perform(get("/v1/receipts/sessions/" + id))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponse errorResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(errorResponse.getMessage()).isEqualTo("Entity not found");
    }
}
