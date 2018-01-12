package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;


public class AttributeResponseDataUtils {
    private static final String PROPERTY_ALLOW_MULTIPLE = "allowMultiple";
    private static final String PROPERTY_REQUIRED = "required";
    private static final String REQUIRED_FIELD_FORMATTER = "formatter";

    public static Optional<AttributeResponse> mockOrganizationResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.ORGANIZATION_NUMBER).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.ORGANIZATION_NUMBER, s, AttributeResponse.TypeEnum.STRING));
    }


    public static Optional<AttributeResponse> mockSocialSecurityNumberResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(AttributeConstant.SOCIAL_SECURITY_NUMBER, REQUIRED_FIELD_FORMATTER));
        return findAttributeValue(listRequest, AttributeConstant.SOCIAL_SECURITY_NUMBER).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.SOCIAL_SECURITY_NUMBER, s, AttributeResponse.TypeEnum.STRING));
    }

    public static List<AttributeResponse> mockAddressResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, AttributeConstant.ADDRESS).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.ADDRESS, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockApprovedAgreementResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.AGREEMENT_ID));
        return findAttributeValues(listRequest, FieldConstant.AGREEMENT_ID).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, FieldConstant.APPROVED_AGREEMENT, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static Optional<AttributeResponse> mockApprovedMarketInfoResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.MARKET_INFO_ID));
        return findAttributeValue(listRequest, FieldConstant.MARKET_INFO_ID).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.APPROVED_MARKET_INFO, s, AttributeResponse.TypeEnum.JSON));
    }

    public static Optional<AttributeResponse> mockApprovedPrivacyResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.PRIVACY_ID));
        return findAttributeValue(listRequest, FieldConstant.PRIVACY_ID).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.APPROVED_PRIVACY, s, AttributeResponse.TypeEnum.JSON));
    }

    public static List<AttributeResponse> mockLicensePlatesResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.LICENSE_PLATE, REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, FieldConstant.LICENSE_PLATE).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, FieldConstant.LICENSE_PLATE, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockPhoneNumbersResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(AttributeConstant.PHONE_NUMBER, REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, AttributeConstant.PHONE_NUMBER).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.PHONE_NUMBER, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());

    }

    public static Optional<AttributeResponse> mockEmailResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return findAttributeValue(listRequest, AttributeConstant.EMAIL).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.EMAIL, s, AttributeResponse.TypeEnum.STRING));

    }

    public static Optional<AttributeResponse> mockTimeZoneResponse (AttributeValueListRequest listRequest) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return findTimeZoneValue(listRequest).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.TIMEZONE, s, AttributeResponse.TypeEnum.STRING));
    }

    private static Optional<String> findTimeZoneValue (AttributeValueListRequest listRequest) {
        return listRequest.getAttributeValues()
                .stream().filter(attributeValueRequest -> attributeValueRequest.getAttributeId().equals(32L)).findAny().map(attributeValueRequest -> attributeValueRequest.getValue());
    }

    private static Optional<String> findAttributeValue (AttributeValueListRequest listRequest, String name) {
        return listRequest.getAttributeValues()
                .stream().filter(attributeValueRequest -> attributeValueRequest.getValue().contains(name)).findAny().map(attributeValueRequest -> attributeValueRequest.getValue());
    }

    private static List<String> findAttributeValues (AttributeValueListRequest listRequest, String name) {
        return listRequest.getAttributeValues().stream().filter(attributeValueRequest -> attributeValueRequest.getValue().contains(name)).map(attributeValueRequest -> attributeValueRequest.getValue()).collect(Collectors.toList());
    }
}
