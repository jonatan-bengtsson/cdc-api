package com.tingcore.cdc.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.response.PaymentInformationResponse;
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


    // TODO this will change when the final customer key implementation is finished
    CustomerKeyResponse toResponse(final AttributeResponse attributeValue, final PaymentInformationResponse paymentInformationResponse) {
        CustomerKeyResponse.Builder builder = CustomerKeyResponse.createBuilder();
        try {
            CustomerKeyResponse customerKeyResponse = objectMapper.readValue(attributeValue.getAttributeValue().getValue(), CustomerKeyResponse.class);
            builder.id(attributeValue.getId())
                    .keyNumber(customerKeyResponse.getKeyNumber())
                    .type(customerKeyResponse.getType())
                    .activeFrom(customerKeyResponse.getActiveFrom())
                    .activeTo(customerKeyResponse.getActiveTo())
                    .serviceKey(customerKeyResponse.getServiceKey())
                    .paymentInformation(paymentInformationMapper.toResponse(paymentInformationResponse))
                    .defaultCurrency(customerKeyResponse.getDefaultCurrency())
                    .name(attributeValue.getName())
                    .organizationId(customerKeyResponse.getOrganizationId())
                    .build();
        } catch (IOException e) {
            throw new InvalidAttributeValueException(attributeValue.getAttributeValue().getId());
        }
        return builder.build();
    }
}
