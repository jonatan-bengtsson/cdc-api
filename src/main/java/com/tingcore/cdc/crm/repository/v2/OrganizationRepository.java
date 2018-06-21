package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v2.InternalApi;
import com.tingcore.users.api.v2.OrganizationsApi;
import com.tingcore.users.model.v2.response.Organization;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationRepository extends AbstractUserServiceRepository {

    private final InternalApi internalApi;
    private final OrganizationsApi organizationsApi;

    OrganizationRepository(ObjectMapper objectMapper, InternalApi internalApi, OrganizationsApi organizationsApi) {
        super(objectMapper);
        this.internalApi = internalApi;
        this.organizationsApi = organizationsApi;
    }

    public ApiResponse<Organization> getOrganizationByUserPrefixInternal(final String userPrefix) {
        return execute(internalApi.getByUserPrefix(userPrefix));
    }

    public ApiResponse<List<Long>> getAssociatedCpoIdsFromEmpId(final long empOrganizationId) {
        return execute(organizationsApi.internalGetAssociatedCpoIds(empOrganizationId));
    }


}
