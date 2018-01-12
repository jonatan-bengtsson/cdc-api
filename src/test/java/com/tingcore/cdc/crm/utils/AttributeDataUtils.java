package com.tingcore.cdc.crm.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.google.common.collect.Maps.newHashMap;

public class AttributeDataUtils {

    private static final String PROPERTY_ALLOW_MULTIPLE = "allowMultiple";
    private static final String PROPERTY_REQUIRED = "required";
    private static final String REQUIRED_FIELD_FORMATTER = "formatter";

    static AttributeResponse createEmailAttributeResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return createAttributeResponse(properties, AttributeConstant.EMAIL, CommonDataUtils.randomEmail(),
                AttributeResponse.TypeEnum.STRING);
    }

    static AttributeResponse createOrganizationResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(AttributeConstant.ORGANIZATION_NUMBER, REQUIRED_FIELD_FORMATTER));
        return createAttributeResponse(properties, AttributeConstant.ORGANIZATION_NUMBER,
                "{\"organizationNumber\": \"124354564\", \"formatter\": \"SE\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    static AttributeResponse createBillingAddressResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(REQUIRED_FIELD_FORMATTER));
        return createAttributeResponse(properties, AttributeConstant.BILLING_ADDRESS,
                "{\"address\": \"Serenity Road 42\", \"postalCode\": \"123 45\", \"city\": \"Stockholm\", \"country\": \"Sweden\", \"formatter\": \"SE\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    static AttributeResponse createFirstNameResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return createAttributeResponse(properties, AttributeConstant.FIRST_NAME, CommonDataUtils.randomUUID(), AttributeResponse.TypeEnum.STRING);
    }

    static AttributeResponse createLastNameResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return createAttributeResponse(properties, AttributeConstant.LAST_NAME, CommonDataUtils.randomUUID(), AttributeResponse.TypeEnum.STRING);
    }

    static AttributeResponse createContactEmailResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return createAttributeResponse(properties, AttributeConstant.CONTACT_EMAIL, CommonDataUtils.randomEmail(),
                AttributeResponse.TypeEnum.STRING);
    }

    static AttributeResponse createVisitingAddress() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(REQUIRED_FIELD_FORMATTER));
        return createAttributeResponse(properties, AttributeConstant.VISITING_ADDRESS,
                "{\"address\": \"Serenity Road 42\", \"postalCode\": \"123 45\", \"city\": \"Stockholm\", \"country\": \"Sweden\", \"formatter\": \"SE\"}",
                AttributeResponse.TypeEnum.JSON);

    }

    static AttributeResponse createVatResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(REQUIRED_FIELD_FORMATTER, AttributeConstant.VAT));
        return createAttributeResponse(properties, AttributeConstant.VAT, "{\"VAT\": \"SE999999999901\", \"formatter\": \"SE\"}",
                AttributeResponse.TypeEnum.JSON);

    }

    static AttributeResponse createPhoneNumberResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(REQUIRED_FIELD_FORMATTER, AttributeConstant.PHONE_NUMBER));
        return createAttributeResponse(properties, AttributeConstant.PHONE_NUMBER, "{\"phoneNumber\": \"0731111111\", \"formatter\": \"SE\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    static AttributeResponse createApprovedAgreementResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList("agreementId"));
        return createAttributeResponse(properties, AttributeConstant.APPROVED_AGREEMENTS, "{\"agreementId\": \"123456\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    public static AttributeResponse createAttributeResponse(final HashMap<String, Object> properties,
                                                             final String attributeName,
                                                             final String value,
                                                             final AttributeResponse.TypeEnum type) {
        final AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setId(CommonDataUtils.getNextId());
        attributeResponse.setProperties(properties);
        attributeResponse.setName(attributeName);
        attributeResponse.setType(type);
        final AttributeValueResponse attributeValue = new AttributeValueResponse();
        attributeResponse.setAttributeValue(attributeValue);
        attributeValue.setValue(value);
        return attributeResponse;
    }

    public static List<AttributeResponse> allAttributes () throws IOException {
        Type listType = new TypeToken<List<AttributeResponse>>(){}.getType();
        return new GsonBuilder().create().fromJson(ATTRIBUTES_LIST_JSON, listType);
    }

    private static final String ATTRIBUTES_LIST_JSON = "[\n" +
            "  {\n" +
            "    \"id\": 20,\n" +
            "    \"name\": \"name\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 21,\n" +
            "    \"name\": \"firstName\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 22,\n" +
            "    \"name\": \"lastName\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 23,\n" +
            "    \"name\": \"approvedAgreements\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"agreementId\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 25,\n" +
            "    \"name\": \"licensePlate\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"licensePlate\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 26,\n" +
            "    \"name\": \"customerNumber\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 27,\n" +
            "    \"name\": \"hasChargingAccess\",\n" +
            "    \"type\": \"boolean\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 28,\n" +
            "    \"name\": \"customerType\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 29,\n" +
            "    \"name\": \"phoneNumber\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 31,\n" +
            "    \"name\": \"language\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 32,\n" +
            "    \"name\": \"timeZone\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 33,\n" +
            "    \"name\": \"organizationNumber\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"required\": [\n" +
            "        \"organizationNumber\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 34,\n" +
            "    \"name\": \"socialSecurityNumber\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"required\": [\n" +
            "        \"socialSecurityNumber\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 35,\n" +
            "    \"name\": \"address\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 36,\n" +
            "    \"name\": \"email\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 37,\n" +
            "    \"name\": \"diagnosticsLink\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 38,\n" +
            "    \"name\": \"VAT\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"required\": [\n" +
            "        \"formatter\",\n" +
            "        \"VAT\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 39,\n" +
            "    \"name\": \"defaultCurrency\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 40,\n" +
            "    \"name\": \"billingAddress\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 41,\n" +
            "    \"name\": \"billingPhoneNumber\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 42,\n" +
            "    \"name\": \"agreement\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"name\",\n" +
            "        \"url\",\n" +
            "        \"agreementId\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 43,\n" +
            "    \"name\": \"contactAddress\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 44,\n" +
            "    \"name\": \"contactPhoneNumber\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\",\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 45,\n" +
            "    \"name\": \"contactFirstName\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 46,\n" +
            "    \"name\": \"contactLastName\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 47,\n" +
            "    \"name\": \"contactNotes\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 48,\n" +
            "    \"name\": \"contactEmail\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 49,\n" +
            "    \"name\": \"visitingAddress\",\n" +
            "    \"type\": \"JSON\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"formatter\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 50,\n" +
            "    \"name\": \"organizationType\",\n" +
            "    \"type\": \"STRING\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  }\n" +
            "]";

}
