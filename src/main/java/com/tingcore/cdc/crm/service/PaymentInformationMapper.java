package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.response.PaymentInformationResponse;

/**
 * @author palmithor
 * @since 2017-11-10
 */
class PaymentInformationMapper {

    PaymentInformationMapper() {

    }

    PaymentInformationResponse toResponse(final PaymentInformationResponse paymentInformationResponse) {
        return PaymentInformationResponse.createBuilder()
                .build();
    }
}
