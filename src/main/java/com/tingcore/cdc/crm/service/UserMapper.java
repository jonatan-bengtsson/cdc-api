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
                .firstName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.FIRST_NAME).orElse(null))
                .lastName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LAST_NAME).orElse(null))
                .email(userResponse.getEmail())
                .organization(OrganizationMapper.toResponse(userResponse.getOrganization()))
                .organizationNumber(AttributeMapper.findStringAttribute(attributes, AttributeConstant.ORGANIZATION_NUMBER).orElse(null))
                .phoneNumbers(AttributeMapper.findPhoneNumberResponses(attributes, AttributeConstant.PHONE_NUMBER))
                .approvedMarketInfo(AttributeMapper.findApprovedMarketInfo(attributes).orElse(null))
                .address(AttributeMapper.findAddressResponse(attributes))
                .timezone(AttributeMapper.findStringAttribute(attributes, AttributeConstant.TIMEZONE).orElse(null))
                .approvedAgreements(AttributeMapper.findApprovedAgreements(attributes))
                .licensePlates(AttributeMapper.findLicensePlateResponse(attributes))
                .socialSecurityNumber(AttributeMapper.findSocialSecurityNumber(attributes).orElse(null))
                .customerNumber(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_NUMBER).orElse(null))
                .customerType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_TYPE).orElse(null))
                .language(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LANGUAGE).orElse(null))
                .organizationPermissions(AttributeMapper.findOrganizationPermissions(userResponse.getOrganizationPermissions()))
                .roles(AttributeMapper.findStringAttributes(attributes, AttributeConstant.ROLES)) // Todo when role structure is set
                .activationDate(AttributeMapper.findDateResponse(attributes, AttributeConstant.ACTIVATION_DATE).orElse(null)) // Todo add to user-service database
                .hasChargingAccess(AttributeMapper.findBooleanAttribute(attributes, AttributeConstant.HAS_CHARGING_ACCESS).orElse(null))
                .build();
    }
}
