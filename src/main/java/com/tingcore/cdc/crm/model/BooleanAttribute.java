package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class BooleanAttribute extends BaseAttributeModel {
    private Boolean value;

    public BooleanAttribute(final Long valueId, final Boolean value) {
        this.id = valueId;
        this.value = value;
    }

    public BooleanAttribute(final boolean value) {
        this.value = value;
    }

    public BooleanAttribute() {
    }

    @JsonProperty(JsonPropertyConstant.VALUE)
    public Boolean getValue() {
        return value;
    }

    public void setValue(final Boolean value) {
        this.value = value;
    }
}
