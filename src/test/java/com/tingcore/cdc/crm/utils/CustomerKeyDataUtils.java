package com.tingcore.cdc.crm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.mapper.CustomerKey;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author palmithor
 * @since 2017-11-09
 */
public class CustomerKeyDataUtils {

    private static final String TYPE_VIRTUAL = "virtual";
    private static ObjectMapper objectMapper;

    private CustomerKeyDataUtils() {

    }

    static {
        objectMapper = JsonUtils.getObjectMapperBuilder().build();
    }


    public static CustomerKeyResponse customerKeyResponse() {
        return CustomerKeyResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .keyNumber(CommonDataUtils.nextZeroPaddedId(10))
                .type(TYPE_VIRTUAL)
                .activatedFrom(getPassedInstant())
                .activatedTo(getFutureInstant())
                .credits(0)
                .defaultCurrency("sek")
                .name(CommonDataUtils.nextZeroPaddedId(5))
                .organizationId(CommonDataUtils.getNextId())
                .build();
    }

    public static AttributeResponse getAttributeResponse() throws JsonProcessingException {
        final AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setId(CommonDataUtils.getNextId());
        attributeResponse.setName("CustomerKey");
        attributeResponse.setType(AttributeResponse.TypeEnum.JSON);
        Map<String, String> properties = new HashMap<>();
        properties.put("required", "[\"namePrefix\", \"provider\", \"activatedFrom\", \"activatedTo\", \"defaultCurrency\", \"credit\", \"creditLimitPerPurchase\", \"creditExpirationDate\"]");
        properties.put("allowMultiple", "true");
        attributeResponse.setProperties(properties);

        AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
        attributeValueResponse.setId(CommonDataUtils.getNextId());
        attributeValueResponse.setValue(objectMapper.writeValueAsString(getCustomerKey()));
        attributeResponse.setAttributeValue(attributeValueResponse);
        return attributeResponse;
    }

    private static CustomerKey getCustomerKey() {
        return new CustomerKey(
                CommonDataUtils.nextZeroPaddedId(10),
                TYPE_VIRTUAL,
                CommonDataUtils.nextZeroPaddedId(10),
                CommonDataUtils.getNextId(),
                getFutureInstant(),
                getPassedInstant(),
                "SEK",
                0,
                1500,
                getFutureInstant()
        );
    }

    private static Instant getPassedInstant() {
        return Instant.now().plus(10, ChronoUnit.DAYS);
    }

    private static Instant getFutureInstant() {
        return Instant.now().minus(10, ChronoUnit.DAYS);
    }
}
