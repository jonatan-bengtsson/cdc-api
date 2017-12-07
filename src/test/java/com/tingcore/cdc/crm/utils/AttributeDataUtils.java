package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;

class AttributeDataUtils {

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

    private static AttributeResponse createAttributeResponse(final HashMap<String, Object> properties,
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

}
