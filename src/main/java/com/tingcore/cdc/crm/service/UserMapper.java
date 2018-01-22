package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.User;
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

    static User toResponse(final UserResponse userResponse) {
        final List<AttributeResponse> attributes = userResponse.getAttributes();
        return User.createBuilder()
                .id(userResponse.getId())
                .firstName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.FIRST_NAME).orElse(null))
                .lastName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LAST_NAME).orElse(null))
                .email(userResponse.getEmail())
                .organization(OrganizationMapper.toResponse(userResponse.getOrganization()))
                .organizationNumber(AttributeMapper.findOrganizationNumber(attributes).orElse(null))
                .phoneNumbers(AttributeMapper.findPhoneNumbers(attributes, AttributeConstant.PHONE_NUMBER))
                .approvedMarketInfos(AttributeMapper.findApprovedMarketInfo(attributes))
                .address(AttributeMapper.findAddress(attributes))
                .timezone(AttributeMapper.findStringAttribute(attributes, AttributeConstant.TIMEZONE).orElse(null))
                .approvedAgreements(AttributeMapper.findApprovedAgreements(attributes))
                .licensePlates(AttributeMapper.findLicensePlates(attributes))
                .socialSecurityNumber(AttributeMapper.findSocialSecurityNumber(attributes).orElse(null))
                .customerNumber(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_NUMBER).orElse(null))
                .customerType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_TYPE).orElse(null))
                .language(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LANGUAGE).orElse(null))
                .activationDate(AttributeMapper.findDate(attributes, AttributeConstant.ACTIVATION_DATE).orElse(null)) // Todo add to user-service database
                .hasChargingAccess(AttributeMapper.findBooleanAttribute(attributes, AttributeConstant.HAS_CHARGING_ACCESS).orElse(null))
                .build();
    }

    public static User attributeListToUserResponse (List<AttributeResponse> attributes) {
        return User.createBuilder()
                .firstName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.FIRST_NAME).orElse(null))
                .lastName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LAST_NAME).orElse(null))
                .name(AttributeMapper.findStringAttribute(attributes, AttributeConstant.NAME).orElse(null))
                .organizationNumber(AttributeMapper.findOrganizationNumber(attributes).orElse(null))
                .phoneNumbers(AttributeMapper.findPhoneNumbers(attributes, AttributeConstant.PHONE_NUMBER))
                .approvedMarketInfos(AttributeMapper.findApprovedMarketInfo(attributes))
                .address(AttributeMapper.findAddress(attributes))
                .timezone(AttributeMapper.findStringAttribute(attributes, AttributeConstant.TIMEZONE).orElse(null))
                .approvedAgreements(AttributeMapper.findApprovedAgreements(attributes))
                .licensePlates(AttributeMapper.findLicensePlates(attributes))
                .socialSecurityNumber(AttributeMapper.findSocialSecurityNumber(attributes).orElse(null))
                .customerType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CUSTOMER_TYPE).orElse(null))
                .language(AttributeMapper.findStringAttribute(attributes, AttributeConstant.LANGUAGE).orElse(null))
                .hasChargingAccess(AttributeMapper.findBooleanAttribute(attributes, AttributeConstant.HAS_CHARGING_ACCESS).orElse(null))
                .approvedPrivacies(AttributeMapper.findApprovedPrivacy(attributes,AttributeConstant.APPROVED_PRIVACY))
                .build();
    }
}
