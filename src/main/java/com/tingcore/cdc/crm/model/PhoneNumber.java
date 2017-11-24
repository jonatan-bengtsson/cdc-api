package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-13.
 */
public class PhoneNumber extends BaseAttribute {

    private String name;
    private String phoneNumber;
    private String formatter;

    public PhoneNumber(Long valueId, String number, String formatter, String name) {
        this.id = valueId;
        this.phoneNumber = number;
        this.formatter = formatter;
        this.name = name;
    }

    public PhoneNumber() {
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("formatter")
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
