package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.users.model.v1.request.CustomerKeyRequest;
import com.tingcore.users.model.v1.response.CustomerKeyResponse;

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

    static CustomerKey toModel(final CustomerKeyResponse response) {
        final List<UserPaymentOption> userPaymentOptions = Optional.ofNullable(response.getUserPaymentOptions())
                .map(userPaymentOptionResponses -> userPaymentOptionResponses.stream()
                        .map(UserPaymentOptionMapper::toModel).collect(Collectors.toList()))
                .orElse(null);

        return CustomerKey.createBuilder()
                .id(response.getId())
                .created(response.getCreated())
                .updated(response.getUpdated())
                .name(response.getName())
                .validFrom(response.getValidFrom())
                .validTo(response.getValidTo())
                .isEnabled(response.getEnabled())
                .keyIdentifier(response.getKeyIdentifier())
                .type(CustomerKeyTypeMapper.toModel(response.getType()))
                .userPaymentOptions(userPaymentOptions)
                .build();
    }

    static CustomerKeyRequest toApiRequest(final CustomerKeyPostRequest incomingRequest) {
        return CustomerKeyRequest.createBuilder()
                .name(incomingRequest.getName())
                .keyIdentifier(incomingRequest.getKeyIdentifier())
                .userPaymentOptions(incomingRequest.getUserPaymentOptionIds())
                .typeId(incomingRequest.getTypeId())
                .enabled(true)
                .validFrom(Instant.now())
                .build();
    }
}
