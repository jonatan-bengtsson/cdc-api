package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v2.InternalOrganizationApi;
import com.tingcore.users.model.v2.response.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationRepository extends AbstractUserServiceRepository {

    private final InternalOrganizationApi internalOrganizationApi;

    OrganizationRepository(ObjectMapper objectMapper, final InternalOrganizationApi internalOrganizationApi) {
        super(objectMapper);
        this.internalOrganizationApi = internalOrganizationApi;
    }

    public ApiResponse<Organization> getOrganizationByUserPrefixInternal(final String userPrefix) {
        return execute(internalOrganizationApi.getByUserPrefix(userPrefix));
    }

    public ApiResponse<List<Long>> getAssociatedCpoIdsFromEmpId(final long empOrganizationId) {
        return execute(internalOrganizationApi.internalGetAssociatedCpoIds(empOrganizationId));
    }


}
