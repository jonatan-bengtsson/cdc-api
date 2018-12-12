package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.exception.PartnershipsServiceException;
import com.tingcore.cdc.crm.repository.v1.PartnershipRepository;
import com.tingcore.partnerships.model.v1.request.CustomerAssociationCreateRequest;
import com.tingcore.partnerships.model.v1.response.CustomerAssociationResponse;
import org.springframework.stereotype.Service;
import com.tingcore.commons.rest.repository.ApiResponse;

@Service
public class PartnershipService {
    private final PartnershipRepository repository;

    public PartnershipService(final PartnershipRepository repository) {
        this.repository = repository;
    }

    public CustomerAssociationResponse createCustomerAssociation(final Long organizationId, final String partnershipId, final CustomerAssociationCreateRequest request) {
        final ApiResponse<CustomerAssociationResponse> response = repository.createCustomerAssociation(organizationId, partnershipId, request);

        return response.getResponseOptional()
                .orElseThrow(() -> new PartnershipsServiceException(response.getError()));
    }
}