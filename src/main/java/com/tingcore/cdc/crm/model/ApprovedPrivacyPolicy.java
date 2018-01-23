package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-12-13.
 */
public class ApprovedPrivacyPolicy extends BaseAttributeModel {

    private String value;

    public ApprovedPrivacyPolicy(Long attributeValueId, String value) {
        this.id = attributeValueId;
        this.value = value;
    }

    public ApprovedPrivacyPolicy() {
    }

    @Override
    public ApprovedPrivacyPolicy copyWithoutId () {
        return new ApprovedPrivacyPolicy(null, this.value);
    }

    @JsonProperty(FieldConstant.VALUE)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
