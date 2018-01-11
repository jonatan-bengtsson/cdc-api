package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moa.mackegard
 * @since 2018-01-10.
 */
public abstract class BaseUpdateCustomerRequest {
    List<AddressCRM> address;
    StringAttribute timezone;
    List<ApprovedAgreement> approvedAgreements;
    ApprovedMarketInfo approvedMarketInfo;
    List<LicensePlate> licensePlates;
    List<PhoneNumber> phoneNumbers;
    StringAttribute language;
    ApprovedPrivacy approvedPrivacy;


    @JsonProperty(FieldConstant.ADDRESS)
    @ApiModelProperty(value = "Private address")
    public List<AddressCRM> getAddress() {
        return address;
    }

    @JsonProperty(FieldConstant.TIME_ZONE)
    @ApiModelProperty(value = "The time zone", example = "GMT+1(Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna)")
    public StringAttribute getTimezone() {
        return timezone;
    }

    @JsonProperty(FieldConstant.APPROVED_AGREEMENT)
    @ApiModelProperty(value = "The ids of all the approved agreements")
    public List<ApprovedAgreement> getApprovedAgreements() {
        return approvedAgreements;
    }

    @JsonProperty(FieldConstant.APPROVES_MARKET_INFO)
    @ApiModelProperty(value = "The id of the approved market info")
    public ApprovedMarketInfo getApprovedMarketInfo() {
        return approvedMarketInfo;
    }

    @JsonProperty(FieldConstant.LICENSE_PLATES)
    @ApiModelProperty(value = "All the registered license plates", example = "ABC 123")
    public List<LicensePlate> getLicensePlates() {
        return licensePlates;
    }

    @JsonProperty(FieldConstant.PHONE_NUMBERS)
    @ApiModelProperty(value = "The phoneNumber for a customer")
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    @JsonProperty(FieldConstant.LANGUAGE)
    @ApiModelProperty(value = "The language of the user")
    public StringAttribute getLanguage() {
        return language;
    }

    @JsonProperty(FieldConstant.APPROVED_PRIVACY)
    @ApiModelProperty(value = "The id of the approved privacy agreement")
    public ApprovedPrivacy getApprovedPrivacy () {
        return approvedPrivacy;
    }

    public OrganizationNumber getOrganizationNumber() {
        return null;
    }

    public SocialSecurityNumber getSocialSecurityNumber() {
        return null;
    }

    public StringAttribute getFirstName() {
        return null;
    }

    public StringAttribute getLastName() {
        return null;
    }

    public StringAttribute getName() {
        return null;
    }

}
