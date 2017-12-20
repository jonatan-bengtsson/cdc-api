package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class Address extends BaseAttributeModel {

    private String address;
    private String addressLine2;
    private String postalCode;
    private String city;
    private String country;
    private String formatter;


    public Address(final Long valueId, final String address, final String addressLine2,
                   final String postalCode, final String city, final String country,
                   final String formatter) {
        this.id = valueId;
        this.address = address;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.formatter = formatter;
    }

    public Address() {
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("formatter")
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

}
