package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.cdc.crm.service.v2.AgreementService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.v2.response.Agreement;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TermsAndConditionsController.class)
public class TermsAndConditionsControllerTest extends ControllerUnitTest {

    @MockBean private AgreementService agreementService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mockAuthorizedUserOrganization();
    }

    @Test
    public void getCurrentTermsAndConditionsApproval() throws Exception {

        given(agreementService.getCurrentTermsAndConditionsApproval(anyLong(), anyLong()))
                .willReturn(new TermsAndConditionsApproval(Agreement.createBuilder().id(CommonDataUtils.getNextId()).active(true).build(), Boolean.TRUE));
        mockMvc.perform(get("/v2/terms-and-conditions/current")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void failGetCurrentTermsAndConditionsApproval() throws Exception {
        given(agreementService.getCurrentTermsAndConditionsApproval(anyLong(), anyLong())).willThrow(new EntityNotFoundException());
        mockMvc.perform(get("/v2/terms-and-conditions/current")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void approveTermsAndConditions() throws Exception {
        doNothing().when(agreementService).approveTermsAndConditions(anyLong(), anyLong());
        mockMvc.perform(put("/v2/terms-and-conditions/approvals/{id}", hashIdService.encode(CommonDataUtils.getNextId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void failApproveTermsAndConditionsInvalidHashId() throws Exception {
        doThrow(EntityNotFoundException.class).when(agreementService).approveTermsAndConditions(anyLong(), anyLong());
        mockMvc.perform(put("/v2/terms-and-conditions/approvals/{id}", CommonDataUtils.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failApproveTermsAndConditions() throws Exception {
        doThrow(new EntityNotFoundException()).when(agreementService).approveTermsAndConditions(anyLong(), anyLong());
        mockMvc.perform(put("/v2/terms-and-conditions/approvals/{id}", hashIdService.encode(CommonDataUtils.getNextId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
