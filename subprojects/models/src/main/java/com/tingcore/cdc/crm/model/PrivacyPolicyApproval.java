package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.users.model.v2.response.Agreement;

/**
 * @author palmithor
 * @since 2018-05-24
 */
public class PrivacyPolicyApproval {

    private final Agreement privacyPolicy;
    private final Boolean approved;

    public PrivacyPolicyApproval(final Agreement privacyPolicy, final Boolean approved) {
        this.privacyPolicy = privacyPolicy;
        this.approved = approved;
    }

    public PrivacyPolicyApproval() {
        this.privacyPolicy = null;
        this.approved = null;
    }

    @JsonProperty(FieldConstant.PRIVACY_POLICY)
    public Agreement getPrivacyPolicy() {
        return privacyPolicy;
    }

    @JsonProperty(FieldConstant.APPROVED)
    public Boolean getApproved() {
        return approved;
    }
}
