package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.users.model.v1.response.UserPaymentOptionResponse;

/**
 * @author palmithor
 * @since 2017-12-19
 */
class UserPaymentOptionMapper {

    static UserPaymentOption toModel(final UserPaymentOptionResponse userPaymentOption) {
        return UserPaymentOption.createBuilder()
                .id(userPaymentOption.getId())
                .created(userPaymentOption.getCreated())
                .updated(userPaymentOption.getUpdated())
                .name(userPaymentOption.getName())
                .description(userPaymentOption.getDescription())
                .paymentOption(userPaymentOption.getPaymentOption())
                .paymentOptionReference(userPaymentOption.getPaymentOptionReference())
                .build();
    }
}
