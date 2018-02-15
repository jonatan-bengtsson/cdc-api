package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author moa.mackegard
 * @since 2018-01-09.
 */
public class AttributeResponseDataUtils {
    private static final String PROPERTY_ALLOW_MULTIPLE = "allowMultiple";
    private static final String PROPERTY_REQUIRED = "required";
    private static final String REQUIRED_FIELD_FORMATTER = "formatter";

    public static Optional<AttributeResponse> mockOrganizationNumberResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.ORGANIZATION_NUMBER, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.ORGANIZATION_NUMBER, s, AttributeResponse.TypeEnum.STRING));
    }

    public static Optional<AttributeResponse> mockSocialSecurityNumberResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(AttributeConstant.SOCIAL_SECURITY_NUMBER, REQUIRED_FIELD_FORMATTER));
        return findAttributeValue(listRequest, AttributeConstant.SOCIAL_SECURITY_NUMBER, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.SOCIAL_SECURITY_NUMBER, s, AttributeResponse.TypeEnum.STRING));
    }

    public static Optional<AttributeResponse> mockApprovedMarketInfoResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.MARKET_INFO_ID));
        return findAttributeValue(listRequest, AttributeConstant.APPROVED_MARKET_INFO, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.APPROVED_MARKET_INFO, s, AttributeResponse.TypeEnum.JSON));
    }

    public static Optional<AttributeResponse> mockApprovedPrivacyPolicyResponse(AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.PRIVACY_ID));
        return findAttributeValue(listRequest, AttributeConstant.APPROVED_PRIVACY_POLICY, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.APPROVED_PRIVACY_POLICY, s, AttributeResponse.TypeEnum.JSON));
    }

    public static Optional<AttributeResponse> mockEmailResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return findAttributeValue(listRequest, AttributeConstant.EMAIL, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.EMAIL, s, AttributeResponse.TypeEnum.STRING));

    }

    public static Optional<AttributeResponse> mockTimeZoneResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        return findAttributeValue(listRequest, AttributeConstant.TIMEZONE, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.TIMEZONE, s, AttributeResponse.TypeEnum.STRING));
    }

    public static Optional<AttributeResponse> mockContactEmailResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.CONTACT_EMAIL, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.CONTACT_EMAIL, s, AttributeResponse.TypeEnum.STRING));
    }

    public static Optional<AttributeResponse> mockContactFirstNameResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.CONTACT_FIRST_NAME, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.CONTACT_FIRST_NAME, s, AttributeResponse.TypeEnum.STRING));
    }

    public static Optional<AttributeResponse> mockContactLastNameResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.CONTACT_LAST_NAME, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.CONTACT_LAST_NAME, s, AttributeResponse.TypeEnum.STRING));

    }

    public static Optional<AttributeResponse> mockContactNotesResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, false);
        return findAttributeValue(listRequest, AttributeConstant.CONTACT_NOTES, cachedAttributes).map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.CONTACT_NOTES, s, AttributeResponse.TypeEnum.STRING));

    }

    public static List<AttributeResponse> mockContactPhoneNumberResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.PHONE_NUMBER, REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, AttributeConstant.CONTACT_PHONE_NUMBER, cachedAttributes).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.CONTACT_PHONE_NUMBER, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockAddressResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Collections.singletonList(REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, AttributeConstant.ADDRESS, cachedAttributes).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.ADDRESS, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockApprovedTermsConditionsResponse(AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.AGREEMENT_ID));
        return findAttributeValues(listRequest, AttributeConstant.APPROVED_TERMS_CONDITIONS, cachedAttributes).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.APPROVED_TERMS_CONDITIONS, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockLicensePlatesResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(FieldConstant.LICENSE_PLATE, REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, FieldConstant.LICENSE_PLATE, cachedAttributes).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, FieldConstant.LICENSE_PLATE, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    public static List<AttributeResponse> mockPhoneNumbersResponse (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        final HashMap<String, Object> properties = newHashMap();
        properties.put(PROPERTY_ALLOW_MULTIPLE, true);
        properties.put(PROPERTY_REQUIRED, Arrays.asList(AttributeConstant.PHONE_NUMBER, REQUIRED_FIELD_FORMATTER));
        return findAttributeValues(listRequest, AttributeConstant.PHONE_NUMBER, cachedAttributes).stream().map(s -> AttributeDataUtils.createAttributeResponse(properties, AttributeConstant.PHONE_NUMBER, s, AttributeResponse.TypeEnum.JSON)).collect(Collectors.toList());
    }

    private static List<String> findAttributeValues (AttributeValueListRequest listRequest, String name, Map<String, Long> cachedAttributes) {
        return listRequest.getAttributeValues().stream()
                .filter(attributeValueRequest -> attributeValueRequest.getAttributeId().equals(cachedAttributes.get(name)))
                .map(attributeValueRequest -> attributeValueRequest.getValue())
                .collect(Collectors.toList());
    }

    private static Optional<String> findAttributeValue (AttributeValueListRequest listRequest, String name, Map<String, Long> cachedAttributes) {
        return listRequest.getAttributeValues()
                .stream()
                .filter(attributeValueRequest -> attributeValueRequest.getAttributeId().equals(cachedAttributes.get(name)))
                .findAny()
                .map(attributeValueRequest -> attributeValueRequest.getValue());
    }
}