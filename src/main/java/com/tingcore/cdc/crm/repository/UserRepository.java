package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.UserResponse;
import org.springframework.stereotype.Repository;

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
}
