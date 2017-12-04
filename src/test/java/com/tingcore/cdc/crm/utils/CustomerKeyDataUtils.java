package com.tingcore.cdc.crm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.response.PaymentInformationResponse;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
                .activeFrom(getPassedInstant())
                .activeTo(getFutureInstant())
                .defaultCurrency("sek")
                .serviceKey(false)
                .chargeGroupIds(new ArrayList<Long>() {{
                    add(1L);
                    add(2L);
                }})
                .name(CommonDataUtils.nextZeroPaddedId(5))
                .organizationId(CommonDataUtils.getNextId())
                .build();
    }

    public static AttributeResponse getAttributeResponse() throws JsonProcessingException {
        final AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setId(CommonDataUtils.getNextId());
        attributeResponse.setName("CustomerKeyResponse");
        attributeResponse.setType(AttributeResponse.TypeEnum.JSON);
        Map<String, String> properties = new HashMap<>();
        properties.put("required", "[\"namePrefix\", \"provider\", \"activeFrom\", \"activeTo\", \"defaultCurrency\", \"credit\", \"creditLimitPerPurchase\", \"creditExpirationDate\"]");
        properties.put("allowMultiple", "true");
        attributeResponse.setProperties(properties);

        AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
        attributeValueResponse.setId(CommonDataUtils.getNextId());
        attributeValueResponse.setValue(objectMapper.writeValueAsString(getCustomerKey()));
        attributeResponse.setAttributeValue(attributeValueResponse);
        return attributeResponse;
    }

    private static CustomerKeyResponse getCustomerKey() {
        return new CustomerKeyResponse(
                CommonDataUtils.getNextId(),
                CommonDataUtils.nextZeroPaddedId(10),
                TYPE_VIRTUAL,
                UUID.randomUUID().toString(),
                CommonDataUtils.getNextId(),
                getPassedInstant(),
                getFutureInstant(),
                false,
                new ArrayList<>(),
                new PaymentInformationResponse(),
                "SEK"
        );
    }

    private static Instant getPassedInstant() {
        return Instant.now().plus(10, ChronoUnit.DAYS);
    }

    private static Instant getFutureInstant() {
        return Instant.now().minus(10, ChronoUnit.DAYS);
    }
}
