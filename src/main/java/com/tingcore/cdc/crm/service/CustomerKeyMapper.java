package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.users.model.CustomerKeyRequest;
import com.tingcore.users.model.CustomerKeyResponse;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author palmithor
 * @since 2017-11-09
 */
class CustomerKeyMapper {

    CustomerKeyMapper() {

    }

    public static CustomerKey toModel(final CustomerKeyResponse response) {
        final List<UserPaymentOption> userPaymentOptions = Optional.ofNullable(response.getUserPaymentOptions())
                .map(userPaymentOptionResponses -> userPaymentOptionResponses.stream()
                        .map(UserPaymentOptionMapper::toModel).collect(Collectors.toList()))
                .orElse(null);

        return CustomerKey.createBuilder()
                .id(response.getId())
                .created(Instant.ofEpochMilli(response.getCreated()))
                .updated(Instant.ofEpochMilli(response.getUpdated()))
                .name(response.getName())
                .validFrom(Instant.ofEpochMilli(response.getValidFrom()))
                .validTo(Instant.ofEpochMilli(response.getValidTo()))
                .isEnabled(response.isIsEnabled())
                .keyIdentifier(response.getKeyIdentifier())
                .type(CustomerKeyTypeMapper.toModel(response.getType()))
                .userPaymentOptions(userPaymentOptions)
                .build();
    }

    public static CustomerKeyRequest toApiRequest(final CustomerKeyPostRequest incomingRequest) {
        final CustomerKeyRequest apiRequest = new CustomerKeyRequest();
        apiRequest.setName(incomingRequest.getName());
        apiRequest.setKeyIdentifier(incomingRequest.getKeyIdentifier());
        apiRequest.setUserPaymentOptions(incomingRequest.getUserPaymentOptionIds());
        apiRequest.setTypeId(incomingRequest.getTypeId());
        apiRequest.setIsEnabled(true);
        apiRequest.setValidFrom(Instant.now().toEpochMilli());
        return apiRequest;
    }
}
