package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.customerkeyorder.client.model.response.Order;
import com.tingcore.users.model.CustomerKeyResponse;
import com.tingcore.users.model.CustomerKeyTypeResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.emptySet;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * @author palmithor
 * @since 2017-12-19
 */
public class CustomerKeyDataUtils {

    private CustomerKeyDataUtils() {

    }


    public static PageResponse<CustomerKeyResponse> randomPageResponse() {
        return new PageResponse<>(
                newArrayList(randomCustomerKeyResponse(), randomCustomerKeyResponse())
        );
    }

    public static CustomerKeyResponse randomCustomerKeyResponse() {
        final Long now = Instant.now().toEpochMilli();
        final CustomerKeyResponse c = new CustomerKeyResponse();
        c.setId(CommonDataUtils.getNextId());
        c.setUpdated(now);
        c.setCreated(now);
        c.setName(CommonDataUtils.randomUUID());
        c.setUserId(CommonDataUtils.getNextId());
        c.setKeyIdentifier(CommonDataUtils.randomUUID().substring(0, 20));
        c.setValidTo(CommonDataUtils.randomTimestamp(true));
        c.setValidFrom(CommonDataUtils.randomTimestamp(false));
        c.setUserPaymentOptions(Collections.singletonList(
                PaymentOptionDataUtils.randomUserPaymentOptionResponse()
        ));
        c.setIsEnabled(true);


        c.setType(randomCustomerKeyTypeResponse());
        return c;
    }

    public static CustomerKey.Builder randomCustomerKey() {
        return CustomerKey.createBuilder()
                .id(CommonDataUtils.getNextId())
                .created(Instant.now())
                .updated(Instant.now())
                .type(randomCustomerKeyType().build())
                .name(CommonDataUtils.randomUUID())
                .validFrom(CommonDataUtils.randomInstant(false))
                .validTo(CommonDataUtils.randomInstant(true))
                .isEnabled(true)
                .keyIdentifier(CommonDataUtils.randomUUID().substring(0, 20))
                .userPaymentOptions(Collections.singletonList(PaymentOptionDataUtils.randomUserPaymentOption().build()));
    }

    public static CustomerKeyType.Builder randomCustomerKeyType() {
        final Instant now = Instant.now();
        return CustomerKeyType.createBuilder()
                .id(CommonDataUtils.getNextId())
                .name(CommonDataUtils.randomUUID())
                .description(CommonDataUtils.randomUUID())
                .created(Instant.now())
                .updated(now);
    }

    public static CustomerKeyPostRequest.Builder randomRequestAllValid() {
        return CustomerKeyPostRequest
                .createBuilder()
                .typeId(CommonDataUtils.getNextId())
                .name(CommonDataUtils.randomUUID())
                .keyIdentifier(CommonDataUtils.randomUUID().substring(0, 20))
                .addUserPaymentOption(CommonDataUtils.getNextId());
    }

    public static CustomerKeyTypeResponse randomCustomerKeyTypeResponse() {
        final Long now = Instant.now().toEpochMilli();
        final CustomerKeyTypeResponse type = new CustomerKeyTypeResponse();
        type.setId(CommonDataUtils.getNextId());
        type.setName(CommonDataUtils.randomUUID());
        type.setDescription(CommonDataUtils.randomUUID());
        type.setCreated(now);
        type.setUpdated(now);
        return type;
    }

    public static CustomerKeyOrderRequest randomCustomerKeyOrderRequest() {
        return new CustomerKeyOrderRequest(
                CommonDataUtils.randomUUID(),
                ThreadLocalRandom.current().nextInt(2, 7)
        );
    }

    public static CustomerKeyOrderRequest randomInvalidCustomerKeyOrderRequest() {
        return new CustomerKeyOrderRequest(
                CommonDataUtils.randomUUID(),
                CommonDataUtils.getNextId().intValue()
        );
    }

    public static Order randomCustomerKeyOrderResponse() {
        Order order = new Order();
        order.setId(CommonDataUtils.getNextId());
        order.setUserId(CommonDataUtils.getNextId());
        order.setOrganizationId(CommonDataUtils.getNextId());
        order.setAddress(CommonDataUtils.randomUUID());
        order.setQuantity(ThreadLocalRandom.current().nextInt(2, 7));
        order.setCustomerKeyType(CommonDataUtils.getNextId());
        order.setCreated(Instant.now());
        order.setState(Order.States.ORDER_PLACED);
        order.setCustomerKeys(emptySet());

        return order;
    }
}
