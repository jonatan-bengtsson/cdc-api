package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.CustomerKeyResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.util.Lists.newArrayList;

/**
 * @author palmithor
 * @since 2017-12-19
 */
public class CustomerKeyDataUtils {

    private CustomerKeyDataUtils() {

    }


    public static PageResponseCustomerKeyResponse randomPageResponse() {
        final PageResponseCustomerKeyResponse r = new PageResponseCustomerKeyResponse();
        r.setContent(newArrayList(randomCustomerKeyResponse(), randomCustomerKeyResponse()));
        return r;
    }

    public static CustomerKeyResponse randomCustomerKeyResponse() {
        final CustomerKeyResponse c = new CustomerKeyResponse();
        c.setId(CommonDataUtils.getNextId());
        c.setUpdated(CommonDataUtils.randomTimestamp(false));
        c.setCreated(CommonDataUtils.randomTimestamp(false));
        c.setName(CommonDataUtils.randomUUID());
        c.setUserId(CommonDataUtils.getNextId());
        c.setKeyIdentifier(CommonDataUtils.randomUUID());
        c.setValidTo(CommonDataUtils.randomTimestamp(true));
        c.setValidFrom(CommonDataUtils.randomTimestamp(false));
        c.setUserPaymentOptions(Collections.singletonList(
                PaymentOptionDataUtils.randomUserPaymentOptionResponse()
        ));
        c.setIsEnabled(true);
        return c;
    }

    public static CustomerKey.Builder randomCustomerKey() {
        return CustomerKey.createBuilder()
                .id(CommonDataUtils.getNextId())
                .created(Instant.now())
                .updated(Instant.now())
                .name(CommonDataUtils.randomUUID())
                .validFrom(CommonDataUtils.randomInstant(false))
                .validTo(CommonDataUtils.randomInstant(true))
                .isEnabled(true)
                .keyIdentifier(CommonDataUtils.randomUUID())
                .userPaymentOptions(Collections.singletonList(PaymentOptionDataUtils.randomUserPaymentOption().build()));

    }

    public static CustomerKeyPostRequest.Builder getRequestAllValid() {
        return CustomerKeyPostRequest
                .createBuilder()
                .name(CommonDataUtils.randomUUID())
                .keyIdentifier(CommonDataUtils.randomUUID())
                .addUserPaymentOption(CommonDataUtils.getNextId());
    }
}
