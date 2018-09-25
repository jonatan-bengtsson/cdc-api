package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.v2.AgreementService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.v2.response.Agreement;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PublicTermsAndConditionsController.class)
public class PublicTermsAndConditionsControllerTest extends ControllerUnitTest {

    @MockBean private AgreementService agreementService;

    @Test
    public void getTermsAndConditionsByUserPrefix() throws Exception{
        given(agreementService.getTermsAndConditionsByUserPrefix(anyString())).willReturn(Agreement.createBuilder().id(CommonDataUtils.getNextId()).content(CommonDataUtils.randomNumberStr(10,10)).build());
        mockMvc.perform(get("/public/v2/terms-and-conditions/userPrefix")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void failGetTermsAndConditionsByUserPrefix() throws Exception{
        given(agreementService.getTermsAndConditionsByUserPrefix(anyString())).willThrow(new EntityNotFoundException());
        mockMvc.perform(get("/public/v2/terms-and-conditions/userPrefix")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
