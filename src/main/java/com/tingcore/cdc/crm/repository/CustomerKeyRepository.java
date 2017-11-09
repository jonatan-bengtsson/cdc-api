package com.tingcore.cdc.crm.repository;

import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.api.AttributesApi;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Repository
public class CustomerKeyRepository {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(100);

    private final AttributesApi attributesApi;

    public CustomerKeyRepository(final AttributesApi attributesApi) {
        this.attributesApi = attributesApi;
    }

    public PageResponse<AttributeResponse> findByUserId(final Long decodedId) {
        // TODO connect to service - this is mocked for now
        final List<AttributeResponse> responseList = new ArrayList<>(5);
        for (int i = 0; i < 1 + (int) (Math.random() * 10); i++) {
            responseList.add(mockAttributeResponse());
        }
        return new PageResponse<>(responseList, null);
    }

    private AttributeResponse mockAttributeResponse() {
        final AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setId(ID_GENERATOR.incrementAndGet());
        attributeResponse.setName("CustomerKey");
        attributeResponse.setType(AttributeResponse.TypeEnum.JSON);
        Map<String, String> properties = new HashMap<>();
        properties.put("required", "[\"namePrefix\", \"type\", \"organizationId\", \"activatedFrom\", \"activatedTo\", \"defaultCurrency\", \"credit\", \"creditLimitPerPurchase\", \"creditExpiration\"]");
        properties.put("allowMultiple", "true");
        attributeResponse.setProperties(properties);

        AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
        attributeValueResponse.setId(ID_GENERATOR.incrementAndGet());
        attributeValueResponse.setValue("{\n" +
                "    \"keyNumber\":\"1510227553885\",\n" +
                "    \"name\": \"value\",\n" +
                "    \"organizationId\": \"N4RE3JrnAa\",\n" +
                "    \"activatedFrom\": 1510227553885,\n" +
                "    \"activatedTo\": 1510227553885,\n" +
                "    \"defaultCurrency\" : \"SEK\", \n" +
                "    \"credit\": 100,\n" +
                "    \"creditLimitPerPurchase\": 1500,\n" +
                "    \"creditExpiration\": 1510227553885,\n" +
                "    \"type\": \"virtual\"\n" +
                "}");
        attributeResponse.setAttributeValue(attributeValueResponse);
        return attributeResponse;
    }
}