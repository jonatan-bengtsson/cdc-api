package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2018-01-10.
 */
public abstract class BaseUpdateCustomerRequest {
    List<AddressCRM> addresses;
    StringAttribute timezone;
    List<ApprovedTermsConditions> approvedTermsConditions;
    List<ApprovedMarketInfo> approvedMarketInfo;
    List<LicensePlate> licensePlates;
    List<PhoneNumber> phoneNumbers;
    StringAttribute language;
    List<ApprovedPrivacyPolicy> approvedPrivacyPolicies;


    @JsonProperty(FieldConstant.ADDRESS)
    @ApiModelProperty(value = "Private addresses")
    public List<AddressCRM> getAddresses() {
        return addresses;
    }

    @JsonProperty(FieldConstant.TIME_ZONE)
    @ApiModelProperty(value = "The time zone", example = "GMT+1(Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna)")
    public StringAttribute getTimezone() {
        return timezone;
    }

    @JsonProperty(FieldConstant.APPROVED_TERMS_CONDITIONS)
    @ApiModelProperty(value = "The ids of all the approved Terms and Conditions agreements")
    public List<ApprovedTermsConditions> getApprovedTermsConditions() {
        return approvedTermsConditions;
    }

    @JsonProperty(FieldConstant.APPROVED_MARKET_INFO)
    @ApiModelProperty(value = "The ids of the approved Market Info agreements")
    public List<ApprovedMarketInfo> getApprovedMarketInfo() {
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

    @JsonProperty(FieldConstant.APPROVED_PRIVACY_POLICIES)
    @ApiModelProperty(value = "The ids of the approved Privacy Policies")
    public List<ApprovedPrivacyPolicy> getApprovedPrivacyPolicies() {
        return approvedPrivacyPolicies;
    }
    
}
