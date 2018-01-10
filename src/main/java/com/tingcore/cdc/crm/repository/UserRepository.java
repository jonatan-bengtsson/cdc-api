package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.UserResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */
@Repository
public class UserRepository extends AbstractUserServiceRepository {

    private final UsersApi usersApi;

    public UserRepository(final ObjectMapper objectMapper, final UsersApi usersApi) {
        super(objectMapper);
        this.usersApi = usersApi;
    }

    public ApiResponse<UserResponse> findById(final Long authorizedUserId, final Boolean includeAttributes) {
        return execute(usersApi.getByIdUsingGET2(authorizedUserId, authorizedUserId, includeAttributes));
    }

    public ApiResponse<UserResponse> getSelf(final String authorizationId, final Boolean includeAttributes) {
        return execute(usersApi.getSelfUsingGET(authorizationId, includeAttributes));
    }

    public ApiResponse<List<AttributeResponse>> putUserAttributeValues (final Long userId, final Long authorizationId, final AttributeValueListRequest attributeValueListRequests) {
            return execute(usersApi.putUserAttributeValuesUsingPUT(userId, attributeValueListRequests, authorizationId));
    }

}
