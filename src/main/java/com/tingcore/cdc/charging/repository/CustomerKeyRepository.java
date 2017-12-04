package com.tingcore.cdc.charging.repository;

import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.users.api.UsersApi;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class CustomerKeyRepository {

    private final UsersApi usersApi;

    public CustomerKeyRepository(final UsersApi usersApi) {
        this.usersApi = notNull(usersApi);
    }

    public List<CustomerKeyResponse> fetchCustomerKeysForUser(final Long userId, final Long authorizedUserId) {
        // TODO implement when user service has the support
        return new ArrayList<>();
    }
}
