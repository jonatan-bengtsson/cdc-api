package com.tingcore.cdc.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.mapper.CustomerKey;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.users.model.AttributeResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Component
class CustomerKeyMapper {

    private final ObjectMapper objectMapper;

    CustomerKeyMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    CustomerKeyResponse toResponse(final AttributeResponse attributeValue) {
        CustomerKey customerKey;
        try {
            customerKey = objectMapper.readValue(attributeValue.getAttributeValue().getValue(), CustomerKey.class);
        } catch (IOException e) {
            throw new InvalidAttributeValueException(attributeValue.getAttributeValue().getId());
        }
        return CustomerKeyResponse.createBuilder()
                .id(attributeValue.getId())
                .keyNumber(customerKey.getKeyNumber())
                .type(customerKey.getType())
                .activatedFrom(customerKey.getActivatedFrom())
                .activatedTo(customerKey.getActivatedTo())
                .credits(customerKey.getCredit())
                .defaultCurrency(customerKey.getDefaultCurrency())
                .name(attributeValue.getName())
                .organizationId(customerKey.getOrganizationId())
                .build();
    }
}
