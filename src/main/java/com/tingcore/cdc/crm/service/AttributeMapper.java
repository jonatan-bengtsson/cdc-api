package com.tingcore.cdc.crm.service;


import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.response.*;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author markusanderssonnoren
 * @since 2017-11-20.
 */
public class AttributeMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeMapper.class);

    static Optional<StringAttributeResponse> findStringAttribute(final List<AttributeResponse> attributes, final String attributeName) {
        return findStringAttributes(attributes, attributeName).stream().findAny();
    }


    private static List<StringAttributeResponse> findStringAttributes(final List<AttributeResponse> attributes, final String attributeName) {
        return findAttributesFromList(attributes, attributeName).stream()
                .map(attributeResponse -> new StringAttributeResponse(attributeResponse.getAttributeValue().getId(), attributeResponse.getAttributeValue().getValue()))
                .collect(Collectors.toList());
    }

    private static List<AttributeResponse> findAttributesFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).collect(Collectors.toList());
    }


    static List<ApprovedAgreementResponse> findApprovedTermsResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.APPROVED_AGREEMENTS);
        List<ApprovedAgreementResponse> approvedTerms = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedAgreementResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, approvedTerms);
    }

    static List<PhoneNumberResponse> findPhoneNumberResponses(final List<AttributeResponse> attributes, String phoneNumberType) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, phoneNumberType);
        List<PhoneNumberResponse> phoneNumbers = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, PhoneNumberResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, phoneNumbers);
    }

    static Optional<AddressResponse> findBillingAddressResponse(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.BILLING_ADDRESS))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    static Optional<ApprovedMarketInfoResponse> findApprovedMarketInfo(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.APPROVED_MARKET_INFO))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedMarketInfoResponse.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    static List<AddressResponse> findAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.ADDRESS);
        List<AddressResponse> addresses = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, addresses);
    }

    static List<LicensePlateResponse> findLicensePlateResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.LICENSE_PLATES);
        List<LicensePlateResponse> licensePlates = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, LicensePlateResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, licensePlates);
    }

    static Optional<SocialSecurityNumberResponse> findSocialSecurityNumber(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.SOCIAL_SECURITY_NUMBER))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, SocialSecurityNumberResponse.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }


    private static List<AttributeResponse> filterAttributeList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(attributeName))
                .collect(Collectors.toList());
    }


    private static <T extends BaseAttributeResponse> List<T> addAttributeIdToAttributeResponses(List<AttributeResponse> attributeResponses, List<T> attributes) {
        IntStream.range(0, attributeResponses.size())
                .forEach(index -> attributes.get(index).setId(attributeResponses.get(index).getAttributeValue().getId()));
        return attributes;
    }

    private static <T> Optional<T> parseAttributeValue(final AttributeResponse attributeResponse, final Class<T> clazz) {
        return JsonUtils.fromJson(attributeResponse.getAttributeValue().getValue(), clazz)
                .map(Optional::of)
                .orElseGet(() -> {
                    LOG.warn("Unable to parse attribute value JSON for attribute {} : {}", attributeResponse.getName(), attributeResponse.getAttributeValue().getValue());
                    return Optional.empty();
                });
    }

    public static Optional<OrganizationNumberResponse> findOrganizationNumber(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.ORGANIZATION_NUMBER))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, OrganizationNumberResponse.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    public static Optional<VatResponse> findVat(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.VAT))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, VatResponse.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    public static List<AddressResponse> findVisitingAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.VISITING_ADDRESS);
        List<AddressResponse> addresses = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, addresses);
    }
}
