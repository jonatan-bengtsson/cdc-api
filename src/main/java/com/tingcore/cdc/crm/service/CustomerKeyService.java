package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.repository.CustomerKeyRepository;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.CustomerKeyResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Service
public class CustomerKeyService {

    private final CustomerKeyRepository customerKeyRepository;


    public CustomerKeyService(final CustomerKeyRepository customerKeyRepository) {
        this.customerKeyRepository = customerKeyRepository;
    }

    public PageResponse<CustomerKey> findByUserId(final Long authorizedUserId) {
        final ApiResponse<PageResponseCustomerKeyResponse> apiResponse = customerKeyRepository.findByUserId(authorizedUserId);
        return apiResponse
                .getResponseOptional()
                .map(apiPageResponse -> new PageResponse<>(apiPageResponse.getContent()
                        .stream()
                        .map(CustomerKeyMapper::toModel)
                        .collect(Collectors.toList())))
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public CustomerKey findByCustomerKeyId(final Long authorizedUserId, final Long customerKeyId) {
        final ApiResponse<CustomerKeyResponse> apiResponse = customerKeyRepository.findById(authorizedUserId, customerKeyId);
        return apiResponse
                .getResponseOptional()
                .map(CustomerKeyMapper::toModel)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public CustomerKey create(final Long authorizedUserId, final CustomerKeyPostRequest customerKeyRequest) {
        final ApiResponse<CustomerKeyResponse> apiResponse = customerKeyRepository.post(authorizedUserId, CustomerKeyMapper.toApiRequest(customerKeyRequest));
        return apiResponse
                .getResponseOptional()
                .map(CustomerKeyMapper::toModel)
                .orElseThrow(() -> new PaymentsApiException(apiResponse.getError()));
    }
}
