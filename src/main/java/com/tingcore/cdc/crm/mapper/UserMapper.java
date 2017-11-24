package com.tingcore.cdc.crm.mapper;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.*;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */
public class UserMapper {

    private static final Logger LOG = LoggerFactory.getLogger(UserMapper.class);

    public UserMapper() {
    }

    public static User toResponse(final UserResponse userResponse) {
        final List<AttributeResponse> attributes = userResponse.getAttributes();
        return User.createBuilder()
                .id(userResponse.getId())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .email(userResponse.getEmail())
                .organizationNumber(findStringAttribute(userResponse, AttributeConstant.ORGANIZATION_NUMBER).orElse(null)) // Todo is this the same as organization number?
                .phoneNumbers(findPhoneNumberResponses(attributes))
                .approvedMarketInfo(findApprovedMarketInfo(userResponse, AttributeConstant.APPROVED_MARKET_INFO).orElse(null))
                .address(findAddressResponse(attributes))
                .timezone(findStringAttribute(userResponse, AttributeConstant.TIMEZONE).orElse(null))
                .approvedTerms(findApprovedTermsResponse(attributes))
                .licensePlates(findLicensePlateResponse(attributes))
                .socialSecurityNumber(findSocialSecurityNumber(attributes).orElse(null))
                .customerNumber(findStringAttribute(userResponse, AttributeConstant.CUSTOMER_NUMBER).orElse(null))
                .customerType(findStringAttribute(userResponse, AttributeConstant.CUSTOMER_TYPE).orElse(null))
                .language(findStringAttribute(userResponse, AttributeConstant.LANGUAGE).orElse(null))
                .build();
        /*
                       .provider(findAttributeValue(userResponse,"provider").orElse(null)) // Todo, add provider attribute to user-service database
                       .connectedOrganizations(findAttributeValueList(userResponse, "connectedOrganizations")) // Todo add to user-service database?
                       .roles(findAttributeValueList(userResponse, "roles"))// Todo add to user-service database?
                       .activationDate(findAttributeValue(userResponse, "activationDate").orElse(null)) // Todo add to user-service database?
                       .expirationDate(findAttributeValue(userResponse, "expirationDate").orElse(null)) // Todo add to user-service database?
                       isLockedOut(findAttributeValue(userResponse, "isLockedOut").orElse(null)) // Todo add to user-service database?
                       .build();
                       */

    }

    private static List<AttributeResponse> filterAttributeList(final List<AttributeResponse> attributeResponseList, final String attributeName) {
        return attributeResponseList.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(attributeName))
                .collect(Collectors.toList());
    }


    private static <T extends BaseAttribute> List<T> addAttributeIdToAttributeResponses(List<AttributeResponse> attributeResponses, List<T> attributes) {
        IntStream.range(0, attributeResponses.size())
                .forEach(index -> attributes.get(index).setId(attributeResponses.get(index).getAttributeValue().getId()));
        return attributes;
    }


    private static List<ApprovedTerms> findApprovedTermsResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.APPROVED_TERMS);
        List<ApprovedTerms> approvedTerms = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedTerms.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, approvedTerms);
    }

    private static List<LicensePlate> findLicensePlateResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.LICENSE_PLATES);
        List<LicensePlate> licensePlates = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, LicensePlate.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, licensePlates);
    }

    private static List<Address> findAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = filterAttributeList(attributes, AttributeConstant.ADDRESS);
        List<Address> addresses = attributeResponses.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, Address.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, addresses);
    }

    private static Optional<ApprovedMarketInfo> findApprovedMarketInfo(final UserResponse userResponse, final String attributeName) {
        return userResponse.getAttributes().stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.APPROVED_MARKET_INFO))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedMarketInfo.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    private static Optional<SocialSecurityNumber> findSocialSecurityNumber(final List<AttributeResponse> attributes) {
        return attributes.stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.SOCIAL_SECURITY_NUMBER))
                .findFirst()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, SocialSecurityNumber.class))
                .filter(Optional::isPresent)
                .orElseGet(Optional::empty);
    }

    private static List<PhoneNumber> findPhoneNumberResponses(final List<AttributeResponse> attributes) {
        final List<AttributeResponse> attributeResponseList = filterAttributeList(attributes, AttributeConstant.PHONE_NUMBER);
        List<PhoneNumber> phoneNumbers = attributeResponseList.stream()
                .map(attributeResponse -> parseAttributeValue(attributeResponse, PhoneNumber.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponseList, phoneNumbers);
    }


    private static Optional<StringAttribute> findStringAttribute(final UserResponse userResponse, final String attributeName) {
        return userResponse.getAttributes().stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(attributeName))
                .map(attributeResponse -> new StringAttribute(attributeResponse.getAttributeValue().getId(), attributeResponse.getAttributeValue().getValue()))
                .findFirst();
    }

    private static <T> Optional<T> parseAttributeValue(final AttributeResponse attributeResponse, final Class<T> clazz) {
        return JsonUtils.fromJson(attributeResponse.getAttributeValue().getValue(), clazz)
                .map(Optional::of)
                .orElseGet(() -> {
                    LOG.warn("Unable to parse attribute value JSON for attribute {} : {}", attributeResponse.getName(), attributeResponse.getAttributeValue().getValue());
                    return Optional.empty();
                });
    }
}
