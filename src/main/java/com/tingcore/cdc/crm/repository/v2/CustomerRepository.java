package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v2.CustomersApi;
import com.tingcore.users.model.v2.response.Customer;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2018-05-21
 */
@Repository
public class CustomerRepository extends AbstractUserServiceRepository {

    private final CustomersApi customersApi;

    public CustomerRepository(final ObjectMapper objectMapper, final CustomersApi customersApi) {
        super(objectMapper);
        this.customersApi = customersApi;
    }

    public ApiResponse<Customer> getSelf(final Long authorizedUserId) {
        return execute(customersApi.getById(authorizedUserId, authorizedUserId));
    }
}
