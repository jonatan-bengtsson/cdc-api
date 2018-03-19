package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.api.CustomerKeysApi;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public ApiResponse<PageResponse<CustomerKeyResponse>> findByUserId(final Long authorizedUserId) {
        final ApiResponse<PageResponseCustomerKeyResponse> apiResponse = execute(usersApi.getUsingGET(authorizedUserId, authorizedUserId));

        return apiResponse.getResponseOptional()
                .map(response -> new ApiResponse<>(new PageResponse<>(response.getContent(), convertGeneratedPagination(response.getPagination()))))
                .orElse(new ApiResponse<>(apiResponse.getError()));
    }

    public ApiResponse<CustomerKeyResponse> findById(final Long authorizedUserId, final Long customerKeyId) {
        return execute(customerKeysApi.getByIdUsingGET3(customerKeyId, authorizedUserId, authorizedUserId));
    }

    public ApiResponse<CustomerKeyResponse> post(final Long authorizedUserId, final CustomerKeyRequest customerKeyRequest) {
        return execute(customerKeysApi.postUsingPOST(customerKeyRequest, authorizedUserId, authorizedUserId));
    }

    public ApiResponse<List<CustomerKeyTypeResponse>> findCustomerKeyTypes() {
        return execute(customerKeysApi.getCustomerKeyTypesUsingGET());
    }

    public ApiResponse<CustomerKeyResponse> addUserPaymentOption(Long customerKeyId, UserPaymentOptionIdRequest userPaymentOptionIdRequest, Long userId, Long authorizedUserId) {
        return execute(customerKeysApi.addPaymentOptionToCustomerKeyUsingPOST(customerKeyId, userPaymentOptionIdRequest, userId, authorizedUserId));
    }
}