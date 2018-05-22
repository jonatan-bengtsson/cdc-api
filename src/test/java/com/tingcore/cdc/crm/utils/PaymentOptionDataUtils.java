package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.v1.response.PaymentOptionResponse;
import com.tingcore.users.model.v1.response.UserPaymentOptionResponse;

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
        return PaymentOptionResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .created(now)
                .updated(now)
                .description(CommonDataUtils.randomUUID())
                .name(CommonDataUtils.randomUUID())
                .build();
    }

    public static UserPaymentOptionResponse randomUserPaymentOptionResponse() {
        final Instant now = Instant.now();
        return UserPaymentOptionResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .updated(now)
                .created(now)
                .name(CommonDataUtils.randomUUID())
                .description(CommonDataUtils.randomUUID())
                .paymentOptionReference(CommonDataUtils.randomUUID())
                .paymentOption(randomPaymentOption())
                .build();
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
