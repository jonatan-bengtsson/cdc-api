package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-12-13.
 */
public class ApprovedPrivacy extends BaseAttributeModel {

    private String privacyId;

    public ApprovedPrivacy (Long attributeValueId, String privacyId) {
        this.id = attributeValueId;
        this.privacyId = privacyId;
    }

    public ApprovedPrivacy () {
    }

    @Override
    public ApprovedPrivacy copyWithoutId () {
        return new ApprovedPrivacy(null, this.privacyId);
    }

    @JsonProperty(FieldConstant.PRIVACY_ID)
    public String getPrivacyId() {
        return privacyId;
    }

    public void setPrivacyId(String privacyId) {
        this.privacyId = privacyId;
    }


}
