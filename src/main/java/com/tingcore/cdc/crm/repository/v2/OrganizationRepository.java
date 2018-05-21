package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.v2.InternalApi;
import com.tingcore.users.model.v2.response.Organization;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRepository extends AbstractUserServiceRepository {

    private final InternalApi internalApi;

    OrganizationRepository(ObjectMapper objectMapper, InternalApi internalApi) {
        super(objectMapper);
        this.internalApi = internalApi;
    }

    public ApiResponse<Organization> getOrganizationByUserPrefixInternal(final String userPrefix) {
        // todo remove authorized user id when new client has been integrated
        return execute(internalApi.getByUserPrefix(null, userPrefix));
    }

}
