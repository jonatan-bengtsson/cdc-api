package com.tingcore.cdc.crm.service;


import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.*;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author markusanderssonnoren
 * @since 2017-11-20.
 */
public class AttributeMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeMapper.class);

    static Optional<StringAttribute> findStringAttribute(final List<AttributeResponse> attributes, final String attributeName) {
        return findStringAttributes(attributes, attributeName).stream().findAny();
    }


    static List<ApprovedAgreement> findApprovedAgreements(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.APPROVED_AGREEMENTS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedAgreement.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static List<PhoneNumber> findPhoneNumbers(final List<AttributeResponse> attributes, final String phoneNumberAttributeName) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, phoneNumberAttributeName);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, PhoneNumber.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Optional<AddressCRM> findBillingAddress(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ADDRESS);

        if (attributeResponse.isPresent()) {
            Optional<AddressCRM> addressResponse = parseAttributeValue(attributeResponse.get(), AddressCRM.class);
            addressResponse.ifPresent(address1 -> address1.setId(attributeResponse.get().getAttributeValue().getId()));
            return addressResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<ApprovedMarketInfo> findApprovedMarketInfo(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.APPROVED_MARKET_INFO);

        if (attributeResponse.isPresent()) {
            Optional<ApprovedMarketInfo> approvedMarketInfoResponse = parseAttributeValue(attributeResponse.get(), ApprovedMarketInfo.class);
            approvedMarketInfoResponse.ifPresent(approvedMarketInfo1 -> approvedMarketInfo1.setId(attributeResponse.get().getAttributeValue().getId()));
            return approvedMarketInfoResponse;
        } else {
            return Optional.empty();
        }
    }


    static List<AddressCRM> findAddress(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.ADDRESS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressCRM.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static List<LicensePlate> findLicensePlates(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.LICENSE_PLATES);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, LicensePlate.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Optional<SocialSecurityNumber> findSocialSecurityNumber(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.SOCIAL_SECURITY_NUMBER);

        if (attributeResponse.isPresent()) {
            Optional<SocialSecurityNumber> socialSecurityNumberResponse = parseAttributeValue(attributeResponse.get(), SocialSecurityNumber.class);
            socialSecurityNumberResponse.ifPresent(socialSecurityNumber1 -> socialSecurityNumber1.setId(attributeResponse.get().getAttributeValue().getId()));
            return socialSecurityNumberResponse;
        } else {
            return Optional.empty();
        }
    }

    public static Optional<OrganizationNumber> findOrganizationNumber(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ORGANIZATION_NUMBER);

        if (attributeResponse.isPresent()) {
            Optional<OrganizationNumber> organizationNumberResponse = parseAttributeValue(attributeResponse.get(), OrganizationNumber.class);
            organizationNumberResponse.ifPresent(organizationNumber1 -> organizationNumber1.setId(attributeResponse.get().getAttributeValue().getId()));
            return organizationNumberResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<Vat> findVat(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.VAT);

        if (attributeResponse.isPresent()) {
            Optional<Vat> vatResponse = parseAttributeValue(attributeResponse.get(), Vat.class);
            vatResponse.ifPresent(vat1 -> vat1.setId(attributeResponse.get().getAttributeValue().getId()));
            return vatResponse;
        } else {
            return Optional.empty();
        }
    }

    static List<AddressCRM> findVisitingAddress(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.VISITING_ADDRESS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressCRM.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Optional<BooleanAttribute> findBooleanAttribute(List<AttributeResponse> attributes, final String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);

        if (attributeResponse.isPresent()) {
            Optional<BooleanAttribute> booleanAttributeResponse = parseAttributeValue(attributeResponse.get(), BooleanAttribute.class);
            booleanAttributeResponse.ifPresent(booleanAttribute1 -> booleanAttribute1.setId(attributeResponse.get().getAttributeValue().getId()));
            return booleanAttributeResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<InstantAttribute> findDate(List<AttributeResponse> attributes, String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);

        if (attributeResponse.isPresent()) {
            Optional<InstantAttribute> instantAttributeResponse = parseAttributeValue(attributeResponse.get(), InstantAttribute.class);
            instantAttributeResponse.ifPresent(instantAttribute1 -> instantAttribute1.setId(attributeResponse.get().getAttributeValue().getId()));
            return instantAttributeResponse;
        } else {
            return Optional.empty();
        }
    }

    public static Optional<ApprovedPrivacy> findApprovedPrivacy(List<AttributeResponse> attributes, String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);
        if (attributeResponse.isPresent()) {
            Optional<ApprovedPrivacy> approvedPrivacy = parseAttributeValue(attributeResponse.get(), ApprovedPrivacy.class);
            approvedPrivacy.ifPresent(a -> a.setId(attributeResponse.get().getAttributeValue().getId()));
            return approvedPrivacy;
        } else {
            return Optional.empty();
        }
    }

    private static List<StringAttribute> findStringAttributes(final List<AttributeResponse> attributes, final String attributeName) {
        return findAttributesFromList(attributes, attributeName).stream()
                .map(attributeResponse -> new StringAttribute(attributeResponse.getAttributeValue().getId(), attributeResponse.getAttributeValue().getValue()))
                .collect(Collectors.toList());
    }

    private static List<AttributeResponse> findAttributesFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).collect(Collectors.toList());
    }

    private static Optional<AttributeResponse> findAttributeFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).findAny();
    }

    private static <T extends BaseAttributeModel> Optional<T> parseAttributeValue(final AttributeResponse attributeResponse, final Class<T> clazz) {
        return JsonUtils.fromJson(attributeResponse.getAttributeValue().getValue(), clazz)
                .map(t -> {
                    t.setId(attributeResponse.getAttributeValue().getId());
                    return Optional.of(t);
                })
                .orElseGet(() -> {
                    LOG.warn("Unable to parse attribute value JSON for attribute {} : {}", attributeResponse.getName(), attributeResponse.getAttributeValue().getValue());
                    return Optional.empty();
                });
    }
}
