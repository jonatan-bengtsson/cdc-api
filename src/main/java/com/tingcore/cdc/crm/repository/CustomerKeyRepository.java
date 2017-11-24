package com.tingcore.cdc.crm.repository;

import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.api.UsersApi;
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

    private final AttributeRepository attributeRepository;
    private final UsersApi usersApi;

    public CustomerKeyRepository(final AttributeRepository attributeRepository, final UsersApi usersApi) {
        this.attributeRepository = attributeRepository;
        this.usersApi = usersApi;
    }

    public PageResponse<AttributeResponse> findByUserId(final Long decodedId) {
        // TODO this will completely change - customer keys won't even be of type attribute response
        final List<AttributeResponse> responseList = new ArrayList<>(5);
        for (int i = 0; i < 1 + (int) (Math.random() * 10); i++) {
            responseList.add(mockAttributeResponse());
        }
        return new PageResponse<>(responseList);
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
                "    \"organizationId\": \"1\",\n" +
                "    \"activatedFrom\": 1510227553885,\n" +
                "    \"activatedTo\": 1510227553885,\n" +
                "    \"defaultCurrency\" : \"SEK\", \n" +
                "    \"type\": \"virtual\"\n" +
                "}");
        attributeResponse.setAttributeValue(attributeValueResponse);
        return attributeResponse;
    }
}