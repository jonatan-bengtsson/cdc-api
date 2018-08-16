package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class OrganizationNumber extends BaseAttributeModel {

    private final String organizationNumber;

    public OrganizationNumber(final Long attributeValueId, final String organizationNumber) {
        super(attributeValueId);
        this.organizationNumber = organizationNumber;
    }

    public OrganizationNumber() {
        this.organizationNumber = null;
    }

    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public OrganizationNumber copyWithoutId () {
        return new OrganizationNumber(null, this.organizationNumber);
    }
}
