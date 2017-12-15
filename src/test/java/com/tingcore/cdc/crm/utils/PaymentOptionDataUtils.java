package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.PaymentOptionResponse;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2017-12-15
 */
public class PaymentOptionDataUtils {

    private PaymentOptionDataUtils() {

    }

    public static PaymentOptionResponse randomPaymentOption() {
        final Instant now = Instant.now();
        final PaymentOptionResponse paymentOptionResponse = new PaymentOptionResponse();
        paymentOptionResponse.setId(CommonDataUtils.getNextId());
        paymentOptionResponse.setCreated(now.toEpochMilli());
        paymentOptionResponse.setUpdated(now.toEpochMilli());
        paymentOptionResponse.setDescription(CommonDataUtils.randomUUID());
        paymentOptionResponse.setName(CommonDataUtils.randomUUID());
        return paymentOptionResponse;
    }
}
