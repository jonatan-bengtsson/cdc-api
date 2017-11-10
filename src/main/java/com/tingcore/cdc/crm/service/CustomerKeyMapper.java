package com.tingcore.cdc.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.PaymentInformation;
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
    private final PaymentInformationMapper paymentInformationMapper;

    CustomerKeyMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.paymentInformationMapper = new PaymentInformationMapper();
    }

    CustomerKeyResponse toResponse(final AttributeResponse attributeValue, final PaymentInformation paymentInformation) {
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
                .activeFrom(customerKey.getActivatedFrom())
                .activeTo(customerKey.getActivatedTo())
                .serviceKey(customerKey.getServiceKey())
                .paymentInformation(paymentInformationMapper.toResponse(paymentInformation))
                .defaultCurrency(customerKey.getDefaultCurrency())
                .name(attributeValue.getName())
                .organizationId(customerKey.getOrganizationId())
                .build();
    }
}
