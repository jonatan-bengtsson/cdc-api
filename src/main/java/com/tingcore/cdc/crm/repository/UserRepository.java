package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */
@Repository
public class UserRepository extends AbstractApiRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    private final UsersApi usersApi;
    private Integer defaultTimeOut;

    public UserRepository(final ObjectMapper objectMapper, final UsersApi usersApi) {
        super(objectMapper);
        this.usersApi = usersApi;
    }

    public ApiResponse<UserResponse> findById(final Long requestedUserId, final Long authorizedUserId, final Boolean includeAttributes) {
        return execute(usersApi.getByIdUsingGET1(requestedUserId, authorizedUserId, includeAttributes));
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
