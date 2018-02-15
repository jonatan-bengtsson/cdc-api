package com.tingcore.cdc.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.service.PaymentOptionService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.PaymentOptionDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author palmithor
 * @since 2017-12-20
 */
@WebMvcTest(value = PaymentOptionsController.class)
public class PaymentOptionsControllerTest extends ControllerUnitTest {


    @MockBean private PaymentOptionService paymentOptionService;


    @Test
    public void getUserPaymentOptions() throws Exception {
        final PageResponse<UserPaymentOption> mockResponse = new PageResponse<>(
                newArrayList(PaymentOptionDataUtils.randomUserPaymentOption().build(), PaymentOptionDataUtils.randomUserPaymentOption().build()));
        given(paymentOptionService.findUserPaymentOptions(authorizedUser.getId())).willReturn(mockResponse);
        MvcResult result = mockMvc.perform(get("/v1/payment-options"))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(mockMvcUtils.pageFromJson(result.getResponse().getContentAsString(), new TypeReference<PageResponse<UserPaymentOption>>() {
        })).isEqualToComparingFieldByFieldRecursively(mockResponse);
    }


    @Test
    public void failGetUserPaymentOptionsGatewayTimeout() throws Exception {
        final Long id = CommonDataUtils.getNextId();
        final String encodedId = hashIdService.encode(id);
        given(paymentOptionService.findUserPaymentOptions(authorizedUser.getId()))
                .willThrow(new UsersApiException(ErrorResponse.gatewayTimeout().build()));
        mockMvc.perform(get("/v1/payment-options", encodedId))
                .andExpect(status().isGatewayTimeout())
                .andExpect(content().string("{\"statusCode\":504,\"status\":\"Gateway Timeout\"}"));
    }
}