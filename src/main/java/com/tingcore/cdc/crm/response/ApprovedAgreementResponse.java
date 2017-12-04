package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedAgreementResponse extends BaseAttributeResponse {

    private String agreementId;

    public ApprovedAgreementResponse(Long attributeValueId, String agreementId) {
        this.id = attributeValueId;
        this.agreementId = agreementId;
    }

    public ApprovedAgreementResponse() {
    }

    @JsonProperty("agreementId")
    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }
}
