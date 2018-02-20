package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class OrganizationNumber extends BaseAttributeModel {

    private final String organizationNumber;
    private final String formatter;

    public OrganizationNumber(final Long attributeValueId, final String organizationNumber, final String formatter) {
        super(attributeValueId);
        this.organizationNumber = organizationNumber;
        this.formatter = formatter;
    }

    public OrganizationNumber() {
        this.organizationNumber = null;
        this.formatter = null;
    }

    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    public String getOrganizationNumber() {
        return organizationNumber;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public OrganizationNumber copyWithoutId () {
        return new OrganizationNumber(null, this.organizationNumber, this.formatter);
    }
}
