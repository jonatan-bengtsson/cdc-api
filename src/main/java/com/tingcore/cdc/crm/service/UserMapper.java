package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.UserResponse;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */
class UserMapper {

    private UserMapper() {
    }

    static GetUserResponse toResponse(final UserResponse userResponse) {
        final List<AttributeResponse> attributes = userResponse.getAttributes();
        return GetUserResponse.createBuilder()
                .id(userResponse.getId())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .email(userResponse.getEmail())
                .organizationNumber(AttributeMapper.findStringAttribute(attributes, AttributeConstant.ORGANIZATION_NUMBER).orElse(null)) // Todo is this the same as organization number?
                .phoneNumbers(AttributeMapper.findPhoneNumberResponses(attributes, "phoneNumber"))
                .approvedMarketInfo(AttributeMapper.findApprovedMarketInfo(attributes).orElse(null))
                .address(AttributeMapper.findAddressResponse(attributes))
                .timezone(AttributeMapper.findStringAttribute(attributes, AttributeConstant.TIMEZONE).orElse(null))
                .approvedAgreement(AttributeMapper.findApprovedTermsResponse(attributes))
                .licensePlates(AttributeMapper.findLicensePlateResponse(attributes))
                .socialSecurityNumber(AttributeMapper.findSocialSecurityNumber(attributes).orElse(null))
                .customerNumber(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_NUMBER).orElse(null))
                .customerType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_TYPE).orElse(null))
                .language(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LANGUAGE).orElse(null))
                /*.provider(findAttributeValue(userResponse, "provider").orElse(null)) // Todo, add provider attribute to user-service database
                .connectedOrganizations(findAttributeValueList(userResponse, "connectedOrganizations")) // Todo add to user-service database?
                .roles(findAttributeValueList(userResponse, "roles"))// Todo add to user-service database?
                .activationDate(findAttributeValue(userResponse, "activationDate").orElse(null)) // Todo add to user-service database?
                .expirationDate(findAttributeValue(userResponse, "expirationDate").orElse(null)) // Todo add to user-service database?
                .isLockedOut(findAttributeValue(userResponse, "isLockedOut").orElse(null)) // Todo add to user-service database?
                */
                .build();


    }
}
