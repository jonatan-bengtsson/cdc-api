package com.tingcore.cdc.crm.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.*;
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

    static AttributeResponse createEmailAttributeResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return createAttributeResponse(properties, AttributeConstant.EMAIL, CommonDataUtils.randomEmail(),
                AttributeResponse.TypeEnum.STRING);
    }

    static AttributeResponse createOrganizationResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(AttributeConstant.ORGANIZATION_NUMBER));
        return createAttributeResponse(properties, AttributeConstant.ORGANIZATION_NUMBER,
                "{\"organizationNumber\": \"124354564\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    static AttributeResponse createBillingAddressResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return createAttributeResponse(properties, AttributeConstant.BILLING_ADDRESS,
                "{\"address\": \"Serenity Road 42\", \"postalCode\": \"123 45\", \"city\": \"Stockholm\", \"country\": \"Sweden\"}",
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
        return createAttributeResponse(properties, AttributeConstant.VISITING_ADDRESS,
                "{\"address\": \"Serenity Road 42\", \"postalCode\": \"123 45\", \"city\": \"Stockholm\", \"country\": \"Sweden\"}",
                AttributeResponse.TypeEnum.JSON);

    }

    static AttributeResponse createVatResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(AttributeConstant.VAT));
        return createAttributeResponse(properties, AttributeConstant.VAT, "{\"VAT\": \"SE999999999901\"}",
                AttributeResponse.TypeEnum.JSON);

    }

    static AttributeResponse createPhoneNumberResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(AttributeConstant.PHONE_NUMBER));
        return createAttributeResponse(properties, AttributeConstant.PHONE_NUMBER, "{\"phoneNumber\": \"0731111111\"}",
                AttributeResponse.TypeEnum.JSON);
    }

    static AttributeResponse createApprovedTermsConditionsResponse() {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList("url", "version"));
        return createAttributeResponse(properties, AttributeConstant.APPROVED_TERMS_CONDITIONS, "{\"url\": \"http://chargedrive.com/terms-and-conditions\", \"version\": \"1.1\"}",
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

    public static OrganizationNumber createOrganizationNumber () {
        return new OrganizationNumber(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(1000,9999));
    }

    public static AddressCRM createAddress () {
        return new AddressCRM(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(1000,2000), CommonDataUtils.randomNumberStr(1000,2000), CommonDataUtils.randomNumberStr(1000,2000),
                CommonDataUtils.randomNumberStr(1000,2000),CommonDataUtils.randomNumberStr(1000,2000), CommonDataUtils.randomNumberStr(1000,2000));
    }

    public static ApprovedTermsConditions createApprovedTermsConditions() {
        return new ApprovedTermsConditions(CommonDataUtils.getNextId(), AttributeConstant.APPROVED_TERMS_CONDITIONS, CommonDataUtils.randomNumberStr(1, 2));
    }

    public static SocialSecurityNumber createSocialSecurityNumber () {
        return new SocialSecurityNumber(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(19000101,20180000));
    }

    public static ApprovedMarketInfo createApprovedMarketInfo () {
        return new ApprovedMarketInfo(CommonDataUtils.getNextId(), AttributeConstant.APPROVED_MARKET_INFO, CommonDataUtils.randomNumberStr(1, 2));
    }

    public static ApprovedPrivacyPolicy createApprovedPrivacy () {
        return new ApprovedPrivacyPolicy(CommonDataUtils.getNextId(), AttributeConstant.APPROVED_PRIVACY_POLICY, CommonDataUtils.randomNumberStr(1, 2));
    }

    public static LicensePlate createLicensePlate () {
        return new LicensePlate(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(000,999)+"-"+CommonDataUtils.randomNumberStr(000,999));
    }

    public static PhoneNumber createPhoneNumber () {
        return new PhoneNumber(CommonDataUtils.getNextId(),CommonDataUtils.randomNumberStr(10000,20000), CommonDataUtils.randomNumberStr(1000,5000));
    }

    public static StringAttribute createStringAttribute(final String value) {
        return new StringAttribute(CommonDataUtils.getNextId(), value);
    }

    public static List<AttributeResponse> allAttributes () throws IOException {
        Type listType = new TypeToken<List<AttributeResponse>>(){}.getType();
        return new GsonBuilder().create().fromJson(ATTRIBUTES_LIST_JSON, listType);
    }

    private static final String ATTRIBUTES_LIST_JSON = "[\n" +
            "  {\n" +
            "    \"id\": 19,\n" +
            "    \"name\": \"name\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 100\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 20,\n" +
            "    \"name\": \"firstName\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 50\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 21,\n" +
            "    \"name\": \"lastName\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 50\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 22,\n" +
            "    \"name\": \"approvedTermsConditions\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"url\",\n" +
            "        \"version\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 24,\n" +
            "    \"name\": \"licensePlate\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"allowOptional\": false,\n" +
            "      \"required\": [\n" +
            "        \"licensePlate\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 25,\n" +
            "    \"name\": \"customerNumber\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^[a-zA-Z0-9- .:]*$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 26,\n" +
            "    \"name\": \"hasChargingAccess\",\n" +
            "    \"type\": \"boolean\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 27,\n" +
            "    \"name\": \"customerType\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^private|business$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 28,\n" +
            "    \"name\": \"phoneNumber\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 30,\n" +
            "    \"name\": \"language\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^[a-z]{2}$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 31,\n" +
            "    \"name\": \"timeZone\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^[U][T][C]([+]|[-])([1-9]|1[012])$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 32,\n" +
            "    \"name\": \"organizationNumber\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"allowOptional\": false,\n" +
            "      \"required\": [\n" +
            "        \"organizationNumber\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 33,\n" +
            "    \"name\": \"socialSecurityNumber\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"allowOptional\": false,\n" +
            "      \"required\": [\n" +
            "        \"socialSecurityNumber\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 34,\n" +
            "    \"name\": \"address\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 35,\n" +
            "    \"name\": \"email\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 36,\n" +
            "    \"name\": \"diagnosticsLink\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 37,\n" +
            "    \"name\": \"VAT\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"allowOptional\": false,\n" +
            "      \"required\": [\n" +
            "        \"VAT\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 38,\n" +
            "    \"name\": \"defaultCurrency\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^([A-Z]){3}$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 39,\n" +
            "    \"name\": \"billingAddress\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 40,\n" +
            "    \"name\": \"billingPhoneNumber\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 41,\n" +
            "    \"name\": \"agreement\",\n" +
            "    \"type\": \"json\",\n" +
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
            "    \"id\": 42,\n" +
            "    \"name\": \"contactAddress\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 43,\n" +
            "    \"name\": \"contactPhoneNumber\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"phoneNumber\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 44,\n" +
            "    \"name\": \"contactFirstName\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 50\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 45,\n" +
            "    \"name\": \"contactLastName\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 50\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 46,\n" +
            "    \"name\": \"contactNotes\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"size\": {\n" +
            "        \"min\": 1,\n" +
            "        \"max\": 140\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 47,\n" +
            "    \"name\": \"contactEmail\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false,\n" +
            "      \"pattern\": \"^[a-z0-9!#$%&*+/=?^_`{|}~.-]+@[a-z0-9-_]+[.][a-z]+$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 48,\n" +
            "    \"name\": \"visitingAddress\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 49,\n" +
            "    \"name\": \"organizationType\",\n" +
            "    \"type\": \"string\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"pattern\": \"^CPO|EMP|AO|CSO$\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 50,\n" +
            "    \"name\": \"approvedPrivacyPolicy\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"url\",\n" +
            "        \"version\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 51,\n" +
            "    \"name\": \"approvedMarketInfo\",\n" +
            "    \"type\": \"json\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": true,\n" +
            "      \"required\": [\n" +
            "        \"url\",\n" +
            "        \"version\"\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 52,\n" +
            "    \"name\": \"activationDate\",\n" +
            "    \"type\": \"number\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 53,\n" +
            "    \"name\": \"systemUser\",\n" +
            "    \"type\": \"boolean\",\n" +
            "    \"properties\": {\n" +
            "      \"allowMultiple\": false\n" +
            "    }\n" +
            "  }\n" +
            "]";

}
