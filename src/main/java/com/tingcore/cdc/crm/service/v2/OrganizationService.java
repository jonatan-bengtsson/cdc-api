package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.OrganizationRepository;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.v2.response.Organization;
import org.springframework.stereotype.Service;

/**
 * @author palmithor
 * @since 2018-05-21
 */
@Service
public class OrganizationService {

private final OrganizationRepository organizationRepository;

    public OrganizationService(final OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


    public Organization getOrganizationByUserPrefix(String userPrefix) {
        final ApiResponse<Organization> apiResponse = organizationRepository.getOrganizationByUserPrefixInternal(userPrefix);
        return apiResponse.getResponseOptional()
                .orElseThrow(() -> new EntityNotFoundException("No organization with the given userPrefix"));
    }
}
