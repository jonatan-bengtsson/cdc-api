package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-12-13.
 */
public class ApprovedPrivacy extends BaseAttributeModel {

    private String value;

    public ApprovedPrivacy (Long attributeValueId, String privacyId) {
        this.id = attributeValueId;
        this.value = privacyId;
    }

    public ApprovedPrivacy () {
    }

    @Override
    public ApprovedPrivacy copyWithoutId () {
        return new ApprovedPrivacy(null, this.value);
    }

    @JsonProperty(FieldConstant.PRIVACY_ID)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
