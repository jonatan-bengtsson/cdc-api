package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2017-11-20.
 */
@Repository
public class UserRepository extends AbstractApiRepository {

    private final UsersApi usersApi;
    private Integer defaultTimeOut;

    public UserRepository(final ObjectMapper objectMapper, final UsersApi usersApi) {
        super(objectMapper);
        this.usersApi = usersApi;
    }

    public ApiResponse<UserResponse> getSelf(final String authorizationId, final Boolean includeAttributes) {
        return execute(usersApi.getSelfUsingGET(authorizationId, includeAttributes));
    }

    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeOut;
    }
}
