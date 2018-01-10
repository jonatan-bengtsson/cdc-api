package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class StringAttribute extends BaseAttributeModel {
    private String value;

    public StringAttribute(Long valueId, String value) {
        this.id = valueId;
        this.value = value;
    }

    public StringAttribute() {
    }

    @JsonProperty(JsonPropertyConstant.VALUE)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringAttribute copyWithoutId () {
        return new StringAttribute(null, this.value);
    }
}
