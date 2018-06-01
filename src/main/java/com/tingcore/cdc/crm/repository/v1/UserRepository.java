package com.tingcore.cdc.crm.repository.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v1.AttributeValuesApi;
import com.tingcore.users.api.v1.UsersApi;
import com.tingcore.users.model.v1.request.AttributeValueListRequest;
import com.tingcore.users.model.v1.response.AttributeResponse;
import com.tingcore.users.model.v1.response.UserResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */
@Repository
public class UserRepository extends AbstractUserServiceRepository {

    private final UsersApi usersApi;
    private final AttributeValuesApi attributeValuesApi;

    public UserRepository(final ObjectMapper objectMapper, final UsersApi usersApi, final AttributeValuesApi attributeValuesApi) {
        super(objectMapper);
        this.usersApi = usersApi;
        this.attributeValuesApi = attributeValuesApi;
    }

    public ApiResponse<UserResponse> findById(final Long authorizedUserId, final Boolean includeAttributes) {
        return execute(usersApi.getUserById(authorizedUserId, authorizedUserId, includeAttributes));
    }

    public ApiResponse<UserResponse> getSelf(final String authorizationId, final Boolean includeAttributes) {
        return execute(usersApi.getSelf(authorizationId, includeAttributes));
    }

    public ApiResponse<List<AttributeResponse>> putUserAttributeValues(final Long authorizedUserId, final AttributeValueListRequest attributeValueListRequests) {
        return execute(attributeValuesApi.updateUserAttributeValues(authorizedUserId, authorizedUserId, attributeValueListRequests));
    }

}
