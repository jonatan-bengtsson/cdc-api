package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.CustomerKeyRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;
import org.springframework.stereotype.Service;

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

    // TODO Include something from payments?
    public PageResponseCustomerKeyResponse findByUserId(final Long decodedId) {
        final ApiResponse<PageResponseCustomerKeyResponse> apiResponse = customerKeyRepository.findByUserId(decodedId);
        return apiResponse
                .getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }
}
