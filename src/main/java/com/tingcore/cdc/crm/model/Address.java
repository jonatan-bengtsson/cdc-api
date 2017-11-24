package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class Address extends BaseAttribute {
    private String street;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String formatter;


    public Address(Long valueId, String street, String address, String postalCode, String city, String country, String formatter) {
        this.id = valueId;
        this.address = address;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.formatter = formatter;
    }

    public Address() {
    }

    @JsonProperty("street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
