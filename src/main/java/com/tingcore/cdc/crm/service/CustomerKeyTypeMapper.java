package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.users.model.CustomerKeyTypeResponse;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2018-01-10
 */
class CustomerKeyTypeMapper {

    private CustomerKeyTypeMapper() {
    }

    public static CustomerKeyType toModel(final CustomerKeyTypeResponse response) {
        return CustomerKeyType.createBuilder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .created(Instant.ofEpochMilli(response.getCreated()))
                .updated(Instant.ofEpochMilli(response.getUpdated()))
                .build();
    }
}
