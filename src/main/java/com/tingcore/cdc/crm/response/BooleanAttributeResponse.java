package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class BooleanAttributeResponse extends BaseAttributeResponse {
    private Boolean value;

    public BooleanAttributeResponse(final Long valueId, final Boolean value) {
        this.id = valueId;
        this.value = value;
    }
    
    public BooleanAttributeResponse(final boolean value) {
        this.value = value;
    }

    public BooleanAttributeResponse() {
    }

    @JsonProperty("isValue")
    public Boolean getValue() {
        return value;
    }

    public void setValue(final Boolean value) {
        this.value = value;
    }
}
