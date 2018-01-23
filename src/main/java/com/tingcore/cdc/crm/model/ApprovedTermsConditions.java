package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedTermsConditions extends BaseAttributeModel {

    private String agreementId;

    public ApprovedTermsConditions(Long attributeValueId, String agreementId) {
        this.id = attributeValueId;
        this.agreementId = agreementId;
    }

    public ApprovedTermsConditions() {
    }

    @JsonProperty(FieldConstant.AGREEMENT_ID)
    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public ApprovedTermsConditions copyWithoutId () {
        return new ApprovedTermsConditions(null,this.agreementId);
    }
}
