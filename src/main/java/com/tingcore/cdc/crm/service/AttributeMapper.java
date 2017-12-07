package com.tingcore.cdc.crm.service;


import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.response.*;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.OrganizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author markusanderssonnoren
 * @since 2017-11-20.
 */
public class AttributeMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeMapper.class);

    static Optional<StringAttributeResponse> findStringAttribute(final List<AttributeResponse> attributes, final String attributeName) {
        return findStringAttributes(attributes, attributeName).stream().findAny();
    }

    static List<StringAttributeResponse> findStringAttributes(final List<AttributeResponse> attributes, final String attributeName) {
        return findAttributesFromList(attributes, attributeName).stream()
                .map(attributeResponse -> new StringAttributeResponse(attributeResponse.getAttributeValue().getId(), attributeResponse.getAttributeValue().getValue()))
                .collect(Collectors.toList());
    }


    static List<ApprovedAgreementResponse> findApprovedAgreements(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.APPROVED_AGREEMENTS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedAgreementResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static List<PhoneNumberResponse> findPhoneNumberResponses(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.PHONE_NUMBER);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, PhoneNumberResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Optional<AddressResponse> findBillingAddressResponse(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ADDRESS);

        if (attributeResponse.isPresent()) {
            Optional<AddressResponse> addressResponse = parseAttributeValue(attributeResponse.get(), AddressResponse.class);
            addressResponse.ifPresent(addressResponse1 -> addressResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return addressResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<ApprovedMarketInfoResponse> findApprovedMarketInfo(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.APPROVED_MARKET_INFO);

        if (attributeResponse.isPresent()) {
            Optional<ApprovedMarketInfoResponse> approvedMarketInfoResponse = parseAttributeValue(attributeResponse.get(), ApprovedMarketInfoResponse.class);
            approvedMarketInfoResponse.ifPresent(approvedMarketInfoResponse1 -> approvedMarketInfoResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return approvedMarketInfoResponse;
        } else {
            return Optional.empty();
        }
    }


    static List<AddressResponse> findAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.ADDRESS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static List<LicensePlateResponse> findLicensePlateResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.LICENSE_PLATES);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, LicensePlateResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static Optional<SocialSecurityNumberResponse> findSocialSecurityNumber(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.SOCIAL_SECURITY_NUMBER);

        if (attributeResponse.isPresent()) {
            Optional<SocialSecurityNumberResponse> socialSecurityNumberResponse = parseAttributeValue(attributeResponse.get(), SocialSecurityNumberResponse.class);
            socialSecurityNumberResponse.ifPresent(socialSecurityNumberResponse1 -> socialSecurityNumberResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return socialSecurityNumberResponse;
        } else {
            return Optional.empty();
        }
    }

    public static Optional<OrganizationNumberResponse> findOrganizationNumber(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ORGANIZATION_NUMBER);

        if (attributeResponse.isPresent()) {
            Optional<OrganizationNumberResponse> organizationNumberResponse = parseAttributeValue(attributeResponse.get(), OrganizationNumberResponse.class);
            organizationNumberResponse.ifPresent(organizationNumberResponse1 -> organizationNumberResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return organizationNumberResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<VatResponse> findVat(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.VAT);

        if (attributeResponse.isPresent()) {
            Optional<VatResponse> vatResponse = parseAttributeValue(attributeResponse.get(), VatResponse.class);
            vatResponse.ifPresent(vatResponse1 -> vatResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return vatResponse;
        } else {
            return Optional.empty();
        }
    }

    static List<AddressResponse> findVisitingAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.VISITING_ADDRESS);
        return attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    static List<GetOrganizationResponse> findOrganizationPermissions(List<OrganizationResponse> organizations) {
        // map OrganizationResponse to GetOrganizationResponse same way as in cdm
        return Collections.emptyList();
    }

    static Optional<BooleanAttributeResponse> findBooleanAttribute(List<AttributeResponse> attributes, final String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);

        if (attributeResponse.isPresent()) {
            Optional<BooleanAttributeResponse> booleanAttributeResponse = parseAttributeValue(attributeResponse.get(), BooleanAttributeResponse.class);
            booleanAttributeResponse.ifPresent(booleanAttributeResponse1 -> booleanAttributeResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return booleanAttributeResponse;
        } else {
            return Optional.empty();
        }
    }

    static Optional<InstantAttributeResponse> findDateResponse(List<AttributeResponse> attributes, String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);

        if (attributeResponse.isPresent()) {
            Optional<InstantAttributeResponse> instantAttributeResponse = parseAttributeValue(attributeResponse.get(), InstantAttributeResponse.class);
            instantAttributeResponse.ifPresent(instantAttributeResponse1 -> instantAttributeResponse1.setId(attributeResponse.get().getAttributeValue().getId()));
            return instantAttributeResponse;
        } else {
            return Optional.empty();
        }
    }

    private static List<AttributeResponse> findAttributesFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).collect(Collectors.toList());
    }

    private static Optional<AttributeResponse> findAttributeFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).findAny();
    }

    private static <T extends BaseAttributeResponse> Optional<T> parseAttributeValue(final AttributeResponse attributeResponse, final Class<T> clazz) {
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
