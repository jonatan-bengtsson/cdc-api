package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.users.model.CustomerKeyRequest;
import com.tingcore.users.model.UserPaymentOptionIdRequest;
import com.tingcore.users.model.UserPaymentOptionResponse;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2017-12-19
 */
class UserPaymentOptionMapper {

    public static UserPaymentOption toModel(final UserPaymentOptionResponse userPaymentOption) {
        return UserPaymentOption.createBuilder()
                .id(userPaymentOption.getId())
                .created(Instant.ofEpochMilli(userPaymentOption.getCreated()))
                .updated(Instant.ofEpochMilli(userPaymentOption.getUpdated()))
                .name(userPaymentOption.getName())
                .description(userPaymentOption.getDescription())
                .paymentOption(userPaymentOption.getPaymentOption())
                .paymentOptionReference(userPaymentOption.getPaymentOptionReference())
                .build();
    }

    /*
    public static UserPaymentOptionIdRequest toApiRequest(final Long userPaymentOptionId) {
        final UserPaymentOptionIdRequest apiRequest = new UserPaymentOptionIdRequest();
        apiRequest.setUserPaymentOptionId(userPaymentOptionId);
        return apiRequest;
    }
    */
}
