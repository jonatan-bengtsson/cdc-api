package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class OrganizationNumberResponse extends BaseAttributeResponse {

    private String organizationNumber;
    private String formatter;

    public OrganizationNumberResponse(final Long attributeValueId, final String organizationNumber, final String formatter) {
        this.id = attributeValueId;
        this.organizationNumber = organizationNumber;
        this.formatter = formatter;
    }

    public OrganizationNumberResponse() {
    }

    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
