package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.PaymentInformation;

/**
 * @author palmithor
 * @since 2017-11-10
 */
class PaymentInformationMapper {

    PaymentInformationMapper() {

    }

    PaymentInformation toResponse(final PaymentInformation paymentInformation) {
        return PaymentInformation.createBuilder()
                .build();
    }
}
