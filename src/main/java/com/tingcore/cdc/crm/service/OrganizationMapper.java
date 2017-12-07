package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.response.GetOrganizationResponse;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.OrganizationResponse;

import java.util.List;

class OrganizationMapper {

    static GetOrganizationResponse toResponse(final OrganizationResponse organizationResponse) {
        final List<AttributeResponse> attributes = organizationResponse.getAttributes();
        return GetOrganizationResponse.createBuilder()
                .id(organizationResponse.getId())
                .name(organizationResponse.getName())
                .email(AttributeMapper.findStringAttribute(attributes, AttributeConstant.EMAIL).orElse(null))
                .visitingAddress(AttributeMapper.findVisitingAddressResponse(attributes))
                .organizationNumber(AttributeMapper.findOrganizationNumber(attributes).orElse(null))
                .phoneNumber(AttributeMapper.findPhoneNumberResponses(attributes))
                .billingTelephone(AttributeMapper.findPhoneNumberResponses(attributes))
                .billingAddress(AttributeMapper.findBillingAddressResponse(attributes).orElse(null))
                .defaultCurrency(AttributeMapper.findStringAttribute(attributes, AttributeConstant.DEFAULT_CURRENCY).orElse(null))
                .vat(AttributeMapper.findVat(attributes).orElse(null))
                .diagnosticsUploadLink(AttributeMapper.findStringAttribute(attributes, AttributeConstant.DIAGNOSTICS_LINK).orElse(null))
                .contactFirstName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_FIRST_NAME).orElse(null))
                .contactLastName(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_LAST_NAME).orElse(null))
                .contactPhoneNumber(AttributeMapper.findPhoneNumberResponses(attributes))
                .contactEmail(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_EMAIL).orElse(null))
                .contactNotes(AttributeMapper.findStringAttribute(attributes, AttributeConstant.CONTACT_NOTES).orElse(null))
                .organizationType(AttributeMapper.findStringAttribute(attributes, AttributeConstant.ORGANIZATION_TYPE).orElse(null))
                .build();
    }

}
