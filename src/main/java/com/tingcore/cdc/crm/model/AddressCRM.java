package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class AddressCRM extends BaseAttributeModel {

    private String address;
    private String addressLine2;
    private String postalCode;
    private String city;
    private String country;
    private String formatter;
    private String name;


    public AddressCRM(final Long valueId, final String address, final String addressLine2,
                      final String postalCode, final String city, final String country,
                      final String formatter, final String name) {
        this.id = valueId;
        this.address = address;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.formatter = formatter;
        this.name = name;
    }

    public AddressCRM() {
    }

    @JsonProperty(FieldConstant.ADDRESS)
    @ApiModelProperty(value = "First line of address", example = "Serinity Road 42")
    @Size(min = 1, max = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty(FieldConstant.ADDRESS_LINE_2)
    @ApiModelProperty(value = "Second line of address", example = "Apartment 2")
    @Size(min = 1, max = 50)
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty(FieldConstant.POSTAL_CODE)
    @ApiModelProperty(value = "Postal code", example = "123 45")
    @Size(min = 1, max = 10)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty(FieldConstant.CITY)
    @ApiModelProperty(value = "City or region", example = "Stockholm")
    @Size(min = 1, max = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty(FieldConstant.COUNTRY)
    @ApiModelProperty(value = "Country", example = "Sweden")
    @Size(min = 1, max = 30)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    @ApiModelProperty(value = "Country code for formatting address", example = "SE")
    @Size(min = 1, max = 4)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(value = "Name to distinguish different types of addresses", example = "Home address")
    @Size(min = 1, max = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressCRM copyWithoutId () {
        return new AddressCRM(null, this.address, this.addressLine2, this.postalCode, this.city, this.country, this.formatter, this.name);
    }

}
