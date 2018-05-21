package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import com.tingcore.users.model.v1.response.CustomerKeyResponse;
import com.tingcore.users.model.v1.response.CustomerKeyTypeResponse;

import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import static com.tingcore.cdc.crm.utils.AttributeDataUtils.createGenericAddress;
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
        final Instant now = Instant.now();
        return CustomerKeyResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .updated(now)
                .created(now)
                .name(CommonDataUtils.randomUUID())
                .userId(CommonDataUtils.getNextId())
                .keyIdentifier(CommonDataUtils.randomUUID().substring(0, 20))
                .validTo(CommonDataUtils.randomInstant(true))
                .validFrom(CommonDataUtils.randomInstant(false))
                .userPaymentOptionResponses((Collections.singletonList(
                        PaymentOptionDataUtils.randomUserPaymentOptionResponse()
                )))
                .enabled(true)
                .type(randomCustomerKeyTypeResponse())
                .build();
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
        final Instant now = Instant.now();
        return CustomerKeyTypeResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .name(CommonDataUtils.randomUUID())
                .description(CommonDataUtils.randomUUID())
                .created(now)
                .updated(now)
                .build();
    }

    public static CustomerKeyOrderRequest randomCustomerKeyOrderRequest() {
        return new CustomerKeyOrderRequest(
                createGenericAddress(),
                ThreadLocalRandom.current().nextInt(2, 7)
        );
    }

    public static CustomerKeyOrderRequest randomInvalidCustomerKeyOrderRequest() {
        return new CustomerKeyOrderRequest(
                createGenericAddress(),
                CommonDataUtils.getNextId().intValue()
        );
    }

    public static CustomerKeyOrder randomCustomerKeyOrderResponse() {
        CustomerKeyOrder order = new CustomerKeyOrder();
        order.setId(CommonDataUtils.getNextId());
        order.setUserId(CommonDataUtils.getNextId());
        order.setOrganizationId(CommonDataUtils.getNextId());
        order.setAddress(createGenericAddress());
        order.setQuantity(ThreadLocalRandom.current().nextInt(2, 7));
        order.setCustomerKeyTypeId(CommonDataUtils.getNextId());
        order.setCreated(Instant.now());
        order.setState(CustomerKeyOrder.States.ORDER_PLACED);
        order.setCustomerKeyOrderItems(emptySet());

        return order;
    }
}
