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

    private final String address;
    private final String addressLine2;
    private final String postalCode;
    private final String city;
    private final String country;
    private final String name;


    public AddressCRM(final Long valueId,
                      final String address,
                      final String addressLine2,
                      final String postalCode,
                      final String city,
                      final String country,
                      final String name) {
        super(valueId);
        this.address = address;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.name = name;
    }

    public AddressCRM() {
        address = null;
        addressLine2 = null;
        postalCode = null;
        city = null;
        country = null;
        name = null;
    }

    @ApiModelProperty(value = "First line of address", example = "Serinity Road 42") @JsonProperty(FieldConstant.ADDRESS) public @Size(min = 1, max = 50) String getAddress() {
        return address;
    }

    @ApiModelProperty(value = "Second line of address", example = "Apartment 2") @JsonProperty(FieldConstant.ADDRESS_LINE_2) public @Size(min = 1, max = 50) String getAddressLine2() {
        return addressLine2;
    }

    @ApiModelProperty(value = "Postal code", example = "123 45") @JsonProperty(FieldConstant.POSTAL_CODE) public @Size(min = 1, max = 10) String getPostalCode() {
        return postalCode;
    }

    @ApiModelProperty(value = "City or region", example = "Stockholm") @JsonProperty(FieldConstant.CITY) public @Size(min = 1, max = 50) String getCity() {
        return city;
    }

    @ApiModelProperty(value = "Country", example = "Sweden") @JsonProperty(FieldConstant.COUNTRY) public @Size(min = 1, max = 30) String getCountry() {
        return country;
    }


    @ApiModelProperty(value = "Name to distinguish different addresses", example = "Home address") @JsonProperty(FieldConstant.NAME) public @Size(min = 1, max = 25) String getName() {
        return name;
    }

    public AddressCRM copyWithoutId() {
        return new AddressCRM(null, address, addressLine2, postalCode, city, country, name);
    }
}
