package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.CustomerKeysApi;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.CustomerKeyRequest;
import com.tingcore.users.model.CustomerKeyResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Repository
public class CustomerKeyRepository extends AbstractUserServiceRepository {

    private final CustomerKeysApi customerKeysApi;
    private final UsersApi usersApi;

    public CustomerKeyRepository(final ObjectMapper objectMapper,
                                 final CustomerKeysApi customerKeysApi,
                                 final UsersApi usersApi) {
        super(objectMapper);
        this.customerKeysApi = customerKeysApi;
        this.usersApi = usersApi;
    }

    public ApiResponse<PageResponseCustomerKeyResponse> findByUserId(final Long authorizedUserId) {
        return execute(usersApi.getUsingGET(authorizedUserId, authorizedUserId));
    }

    public ApiResponse<CustomerKeyResponse> findById(final Long authorizedUserId, final Long customerKeyId) {
        return execute(customerKeysApi.getByIdUsingGET3(customerKeyId, authorizedUserId, authorizedUserId));
    }

    public ApiResponse<CustomerKeyResponse> post(final Long authorizedUserId, final CustomerKeyRequest customerKeyRequest) {
        return execute(customerKeysApi.postUsingPOST2(customerKeyRequest, authorizedUserId, authorizedUserId));
    }
}