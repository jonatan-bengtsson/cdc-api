package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class StringAttributeResponse extends BaseAttributeResponse {
    private String value;

    public StringAttributeResponse(Long valueId, String value) {
        this.id = valueId;
        this.value = value;
    }

    public StringAttributeResponse() {
    }
    
    @JsonProperty(JsonPropertyConstant.VALUE)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
