package com.tingcore.cdc.charging.repository;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.model.UserId;
import com.tingcore.cdc.model.UserReference;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.AttributeResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class CustomerKeyRepository {

    // TODO answer to the question below: Nope it is not certain. The name however is always the same so you can take a look at AttributeRepository.
    private final Long CUSTOMER_KEY_ATTRIBUTE_ID = 30L; // TODO is this static and same in all envs?

    private final UsersApi usersApi;

    public CustomerKeyRepository(final UsersApi usersApi) {
        this.usersApi = notNull(usersApi);
    }

    public List<CustomerKey> fetchCustomerKeysForUser(final UserId userId,
                                                      final UserReference userReference) {
        try {
            final AttributeResponse response = usersApi.getUserAttributeValueByIdUsingGET(userId.value, CUSTOMER_KEY_ATTRIBUTE_ID, Long.valueOf(userReference.value)).get();

            // TODO parse JSON array?
            // response.getProperties();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
