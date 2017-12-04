package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.CustomerKeyRepository;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.commons.rest.PageResponse;
import org.springframework.stereotype.Service;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Service
public class CustomerKeyService {

    private final CustomerKeyRepository customerKeyRepository;
    private final CustomerKeyMapper customerKeyMapper;

    public CustomerKeyService(final CustomerKeyRepository customerKeyRepository,
                              final CustomerKeyMapper customerKeyMapper) {
        this.customerKeyRepository = customerKeyRepository;
        this.customerKeyMapper = customerKeyMapper;
    }


    // TODO Test and fix when customer key service is implemented
    public PageResponse<CustomerKeyResponse> findByUserId(final Long decodedId) {
        return customerKeyRepository.findByUserId(decodedId)
                .map(attributeResponse -> customerKeyMapper.toResponse(attributeResponse, null));
    }
}
