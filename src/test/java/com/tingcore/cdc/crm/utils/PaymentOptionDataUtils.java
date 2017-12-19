package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.response.UserPaymentOption;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.PaymentOptionResponse;
import com.tingcore.users.model.UserPaymentOptionResponse;

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

    public static UserPaymentOptionResponse randomUserPaymentOptionResponse() {
        final Instant now = Instant.now();
        final UserPaymentOptionResponse r = new UserPaymentOptionResponse();
        r.setId(CommonDataUtils.getNextId());
        r.setUpdated(now.toEpochMilli());
        r.setCreated(now.toEpochMilli());
        r.setName(CommonDataUtils.randomUUID());
        r.setDescription(CommonDataUtils.randomUUID());
        r.setPaymentOptionReference(CommonDataUtils.randomUUID());
        r.setPaymentOption(randomPaymentOption());
        return r;
    }


    public static UserPaymentOption.Builder randomUserPaymentOption() {
        final Instant now = Instant.now();
        return UserPaymentOption.createBuilder()
                .id(CommonDataUtils.getNextId())
                .created(now)
                .updated(now)
                .name(CommonDataUtils.randomUUID())
                .description(CommonDataUtils.randomUUID())
                .paymentOptionReference(CommonDataUtils.randomUUID())
                .paymentOption(randomPaymentOption());
    }
}
