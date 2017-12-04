package com.tingcore.cdc.crm.response;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
