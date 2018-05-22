package com.tingcore.cdc.crm.utils;

import com.google.common.collect.Lists;
import com.tingcore.cdc.crm.model.Organization;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.v1.response.OrganizationResponse;

import java.time.Instant;


public class OrganizationDataUtils {
    public static Organization createGetOrganizationResponse() {
        return Organization.createBuilder()
                .name(CommonDataUtils.randomUUID())
                .id(CommonDataUtils.getNextId())
                .build();
    }

    static OrganizationResponse.Builder createOrganizationResponse() {
        return OrganizationResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .name(CommonDataUtils.randomUUID())
                .created(Instant.now())
                .attributes(Lists.newArrayList(
                        AttributeDataUtils.createEmailAttributeResponse(),
                        AttributeDataUtils.createOrganizationResponse(),
                        AttributeDataUtils.createBillingAddressResponse(),
                        AttributeDataUtils.createContactEmailResponse(),
                        AttributeDataUtils.createVisitingAddress(),
                        AttributeDataUtils.createVatResponse()
                ));
    }
}
