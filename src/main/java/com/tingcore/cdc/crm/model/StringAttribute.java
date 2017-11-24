package com.tingcore.cdc.crm.model;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class StringAttribute extends BaseAttribute {
    private String value;

    public StringAttribute(Long valueId, String value) {
        this.id = valueId;
        this.value = value;
    }

    public StringAttribute() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
