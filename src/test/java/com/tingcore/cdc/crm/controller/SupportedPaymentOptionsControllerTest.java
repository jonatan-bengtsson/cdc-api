package com.tingcore.cdc.crm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.PaymentOptionService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.PaymentOptionDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.PaymentOptionResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author palmithor
 * @since 2017-12-15
 */
@WebMvcTest(value = SupportedPaymentOptionsController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.filter"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.security")
        })
public class SupportedPaymentOptionsControllerTest extends ControllerUnitTest {

    @MockBean PaymentOptionService paymentOptionService;


    @Test
    public void getSupportedPaymentOptions() throws Exception {
        final PaymentOptionResponse mockResponse = PaymentOptionDataUtils.randomPaymentOption();
        given(paymentOptionService.findSupportedPaymentOptions(authorizedUser.getUser().getId())).willReturn(Collections.singletonList(mockResponse));
        MvcResult result = mockMvc.perform(get("/v1/supported-payment-options"))
                .andExpect(status().isOk())
                .andReturn();
        final List<PaymentOptionResponse> responseList = mockMvcUtils.fromJson(result.getResponse().getContentAsString(), new TypeReference<List<PaymentOptionResponse>>() {
        });
        assertThat(responseList).hasSize(1);
        assertThat(responseList).containsExactly(mockResponse);
    }

    @Test
    public void failGetSupportedPaymentOptionsUnauthorized() throws Exception {
        given(paymentOptionService.findSupportedPaymentOptions(authorizedUser.getUser().getId())).willThrow(new UsersApiException(ErrorResponse.unauthorized().build()));
        mockMvc.perform(get("/v1/supported-payment-options"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("{\"statusCode\":401,\"status\":\"Unauthorized\"}"));
    }
}