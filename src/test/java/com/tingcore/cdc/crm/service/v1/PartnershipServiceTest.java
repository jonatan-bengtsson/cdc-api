package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.repository.v1.PartnershipRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.partnerships.model.v1.request.CustomerAssociationCreateRequest;
import com.tingcore.partnerships.model.v1.response.CustomerAssociationResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PartnershipServiceTest {

    @MockBean
    private PartnershipRepository partnershipRepository;

    private PartnershipService service;

    @Before
    public void setUp() {
        this.service = new PartnershipService(partnershipRepository);
    }

    @Test
    public void createCustomerAssociation() {
        final Long organizationId = 1L;
        final String partnershipId = UUID.randomUUID().toString();
        final Long internalCustomerId = 666L;
        final String externalCustomerId = "id-123";

        final CustomerAssociationCreateRequest request = new CustomerAssociationCreateRequest(internalCustomerId, externalCustomerId);
        final CustomerAssociationResponse mockedResponse = new CustomerAssociationResponse(
                UUID.randomUUID().toString(),
                internalCustomerId,
                externalCustomerId,
                new Date()
        );

        given(partnershipRepository.createCustomerAssociation(organizationId, partnershipId, request))
                .willReturn(new ApiResponse<>(mockedResponse));

        final CustomerAssociationResponse response = service.createCustomerAssociation(organizationId, partnershipId, request);
        assertThat(response.equals(mockedResponse));
    }
}
