package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.exception.PartnershipsServiceException;
import com.tingcore.cdc.crm.service.v1.PartnershipService;
import com.tingcore.cdc.crm.service.v1.UserService;
import com.tingcore.commons.api.model.Organization;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.partnerships.model.v1.response.CustomerAssociationResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(value = PartnershipController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.filter"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.tingcore.cdc.security")
        })
public class PartnershipControllerTest extends ControllerUnitTest {
    @MockBean
    private PartnershipService partnershipService;
    @MockBean
    private UserService userService;

    @Test
    public void createCustomerAssociation() throws Exception {
        final Long organizationId = 1L;
        final Long internalCustomerId = authorizedUser.getId();
        final String externalCustomerId = "uuid-123";
        final String partnershipId = UUID.randomUUID().toString();
        final String fakePartnershipId = UUID.randomUUID().toString();

        final CustomerAssociationResponse mockedResponse = new CustomerAssociationResponse(
                partnershipId,
                internalCustomerId,
                externalCustomerId,
                new Date()
        );

        when(authorizedUser.getOrganization()).thenReturn(Organization.createBuilder().id(organizationId).build());
        when(partnershipService.createCustomerAssociation(eq(organizationId), eq(partnershipId), any())).thenReturn(mockedResponse);
        when(partnershipService.createCustomerAssociation(eq(organizationId), eq(fakePartnershipId), any())).thenThrow(new PartnershipsServiceException(ErrorResponse.notFound().build()));

        MvcResult result = mockMvc.perform(post("/v1/partnerships/{partnershipId}/customers/{externalCustomerId}", partnershipId, externalCustomerId)
                )
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();

        mockMvc.perform(post("/v1/partnerships/{partnershipId}/customers/{externalCustomerId}", fakePartnershipId, externalCustomerId))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
