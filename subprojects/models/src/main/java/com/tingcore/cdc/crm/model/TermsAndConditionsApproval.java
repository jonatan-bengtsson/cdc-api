package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.users.model.v2.response.Agreement;

/**
 * @author palmithor
 * @since 2018-05-24
 */
public class TermsAndConditionsApproval {

    private final Agreement termsAndConditions;
    private final Boolean approved;

    public TermsAndConditionsApproval(final Agreement termsAndConditions, final Boolean approved) {
        this.termsAndConditions = termsAndConditions;
        this.approved = approved;
    }

    public TermsAndConditionsApproval() {
        termsAndConditions = null;
        approved = null;
    }

    @JsonProperty(FieldConstant.TERMS_AND_CONDITIONS)
    public Agreement getTermsAndConditions() {
        return termsAndConditions;
    }

    @JsonProperty(FieldConstant.APPROVED)
    public Boolean getApproved() {
        return approved;
    }
}
