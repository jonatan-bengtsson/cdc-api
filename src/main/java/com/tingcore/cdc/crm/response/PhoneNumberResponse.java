package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-13.
 */
public class PhoneNumberResponse extends BaseAttributeResponse {

    private String name;
    private String phoneNumber;
    private String formatter;

    public PhoneNumberResponse(Long valueId, String number, String formatter, String name) {
        this.id = valueId;
        this.phoneNumber = number;
        this.formatter = formatter;
        this.name = name;
    }

    public PhoneNumberResponse() {
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
