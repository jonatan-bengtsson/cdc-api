package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.users.model.v1.response.CustomerKeyTypeResponse;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2018-01-10
 */
class CustomerKeyTypeMapper {

    private CustomerKeyTypeMapper() {
    }

    static CustomerKeyType toModel(final CustomerKeyTypeResponse response) {
        return CustomerKeyType.createBuilder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .created(response.getCreated())
                .updated(response.getUpdated())
                .build();
    }
}
