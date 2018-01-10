package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedAgreement extends BaseAttributeModel {

    private String agreementId;

    public ApprovedAgreement(Long attributeValueId, String agreementId) {
        this.id = attributeValueId;
        this.agreementId = agreementId;
    }

    public ApprovedAgreement() {
    }

    @JsonProperty(FieldConstant.AGREEMENT_ID)
    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public ApprovedAgreement copyWithoutId () {
        return new ApprovedAgreement(null,this.agreementId);
    }
}
