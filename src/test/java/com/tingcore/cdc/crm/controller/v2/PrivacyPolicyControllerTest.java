package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.model.PrivacyPolicyApproval;
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

@WebMvcTest(value = PrivacyPolicyController.class)
public class PrivacyPolicyControllerTest extends ControllerUnitTest {

    @MockBean private AgreementService agreementService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        mockAuthorizedUserOrganization();
    }

    @Test
    public void getCurrentPrivacyPolicyApproval() throws Exception {

        given(agreementService.getCurrentPrivacyPolicyApproval(anyLong(), anyLong()))
                .willReturn(new PrivacyPolicyApproval(Agreement.createBuilder().id(CommonDataUtils.getNextId()).active(true).build(), Boolean.TRUE));
        mockMvc.perform(get("/v2/privacy-policies/current")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void failGetCurrentPrivacyPolicyApproval() throws Exception {
        given(agreementService.getCurrentPrivacyPolicyApproval(anyLong(), anyLong())).willThrow(new EntityNotFoundException());
        mockMvc.perform(get("/v2/privacy-policies/current")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void approvePrivacyPolicy() throws Exception {
        doNothing().when(agreementService).approvePrivacyPolicy(anyLong(), anyLong());
        mockMvc.perform(put("/v2/privacy-policies/approvals/{id}", hashIdService.encode(CommonDataUtils.getNextId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void failApprovePrivacyPolicyInvalidHashId() throws Exception {
        doThrow(EntityNotFoundException.class).when(agreementService).approvePrivacyPolicy(anyLong(), anyLong());
        mockMvc.perform(put("/v2/privacy-policies/approvals/{id}", CommonDataUtils.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void failApprovePrivacyPolicy() throws Exception {
        doThrow(new EntityNotFoundException()).when(agreementService).approvePrivacyPolicy(anyLong(), anyLong());
        mockMvc.perform(put("/v2/privacy-policies/approvals/{id}", hashIdService.encode(CommonDataUtils.getNextId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
