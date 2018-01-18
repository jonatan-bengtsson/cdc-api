package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.Organization;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.OrganizationResponse;

import java.util.List;

class OrganizationMapper {

    static Organization toResponse(final OrganizationResponse organizationResponse) {
        final List<AttributeResponse> attributes = organizationResponse.getAttributes();
        return Organization.createBuilder()
                .id(organizationResponse.getId())
                .name(organizationResponse.getName())
                .email(AttributeMapper.findStringAttribute(attributes, AttributeConstant.EMAIL).orElse(null))
                .visitingAddress(AttributeMapper.findVisitingAddress(attributes))
                .organizationNumber(AttributeMapper.findOrganizationNumber(attributes).orElse(null))
                .phoneNumber(AttributeMapper.findPhoneNumbers(attributes, AttributeConstant.PHONE_NUMBER))
                .billingTelephone(AttributeMapper.findPhoneNumbers(attributes, AttributeConstant.BILLING_TELEPHONE))
                .billingAddress(AttributeMapper.findBillingAddress(attributes).orElse(null))
                .defaultCurrency(AttributeMapper.findStringAttribute(attributes, AttributeConstant.DEFAULT_CURRENCY).orElse(null))
                .vat(AttributeMapper.findVat(attributes).orElse(null))
                .diagnosticsUploadLink(AttributeMapper.findStringAttribute(attributes, AttributeConstant.DIAGNOSTICS_LINK).orElse(null))
                .contactFirstName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_FIRST_NAME).orElse(null))
                .contactLastName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_LAST_NAME).orElse(null))
                .contactPhoneNumber(AttributeMapper.findPhoneNumbers(attributes, AttributeConstant.CONTACT_PHONE_NUMBER))
                .contactEmail(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_EMAIL).orElse(null))
                .contactNotes(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_NOTES).orElse(null))
                .organizationType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.ORGANIZATION_TYPE).orElse(null))
                .build();
    }

}
