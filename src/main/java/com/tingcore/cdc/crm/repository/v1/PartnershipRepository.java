package com.tingcore.cdc.crm.repository.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.partnerships.client.PartnershipsServiceApi;
import com.tingcore.partnerships.model.v1.request.CustomerAssociationCreateRequest;
import com.tingcore.partnerships.model.v1.response.CustomerAssociationResponse;
import org.springframework.stereotype.Repository;

@Repository
public class PartnershipRepository extends AbstractUserServiceRepository {
    private final PartnershipsServiceApi partnershipsApi;

    public PartnershipRepository(final ObjectMapper objectMapper, final PartnershipsServiceApi partnershipsApi) {
        super(objectMapper);
        this.partnershipsApi = partnershipsApi;
    }

    public ApiResponse<CustomerAssociationResponse> createCustomerAssociation(final Long organizationId, final String partnershipId, final CustomerAssociationCreateRequest request) {
        return execute(partnershipsApi.createCustomerAssociation(organizationId, partnershipId, request));
    }
}
