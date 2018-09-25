package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.CustomerRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v2.request.PrivateCustomerUpdateRequest;
import com.tingcore.users.model.v2.response.Customer;
import com.tingcore.users.model.v2.response.PrivateCustomer;
import org.springframework.stereotype.Service;

/**
 * @author palmithor
 * @since 2018-05-21
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(final Long authorizedUserId) {
        final ApiResponse<Customer> apiResponse = customerRepository.getSelf(authorizedUserId);
        return apiResponse.getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public Customer update(final Long authorizedUserId, final PrivateCustomerUpdateRequest request) {
        final ApiResponse<PrivateCustomer> apiResponse = customerRepository.update(authorizedUserId, request);
        return apiResponse.getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }
}
