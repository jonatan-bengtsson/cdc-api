package com.tingcore.cdc.crm.utils;

import com.google.common.collect.Lists;
import com.tingcore.cdc.crm.model.Organization;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.OrganizationResponse;

import java.time.Instant;


public class OrganizationDataUtils {
    public static Organization createGetOrganizationResponse() {
        return Organization.createBuilder()
                .name(CommonDataUtils.randomUUID())
                .id(CommonDataUtils.getNextId())
                .build();
    }

    public static OrganizationResponse createOrganizationResponse() {
        final OrganizationResponse response = new OrganizationResponse();
        response.setId(CommonDataUtils.getNextId());
        response.setName(CommonDataUtils.randomUUID());
        response.setCreated(Instant.now().toEpochMilli());
        response.setAttributes(Lists.newArrayList(
                AttributeDataUtils.createEmailAttributeResponse(),
                AttributeDataUtils.createOrganizationResponse(),
                AttributeDataUtils.createBillingAddressResponse(),
                AttributeDataUtils.createContactEmailResponse(),
                AttributeDataUtils.createVisitingAddress(),
                AttributeDataUtils.createVatResponse()
        ));
        return response;
    }
}
