package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.model.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class UpdatePrivateCustomerRequest extends BaseUpdateCustomerRequest {
    private StringAttribute firstName;
    private StringAttribute lastName;
    private SocialSecurityNumber socialSecurityNumber;

    public UpdatePrivateCustomerRequest(StringAttribute firstName,
                                        StringAttribute lastName,
                                        List<AddressCRM> address,
                                        StringAttribute timezone,
                                        List<ApprovedTermsConditions> approvedTermsConditions,
                                        List<ApprovedMarketInfo> approvedMarketInfo,
                                        List<LicensePlate> licensePlates,
                                        SocialSecurityNumber socialSecurityNumber,
                                        List<PhoneNumber> phoneNumbers,
                                        StringAttribute language,
                                        List<ApprovedPrivacyPolicy> approvedPrivacyPolicies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = address;
        this.timezone = timezone;
        this.approvedTermsConditions = approvedTermsConditions;
        this.approvedMarketInfo = approvedMarketInfo;
        this.licensePlates = licensePlates;
        this.socialSecurityNumber = socialSecurityNumber;
        this.phoneNumbers = phoneNumbers;
        this.language = language;
        this.approvedPrivacyPolicies = approvedPrivacyPolicies;
    }

    public UpdatePrivateCustomerRequest() {
    }

    @JsonProperty(FieldConstant.FIRST_NAME)
    @ApiModelProperty(value = "Private customer first name", example = "Anna")
    public StringAttribute getFirstName() {
        return firstName;
    }

    @JsonProperty(FieldConstant.LAST_NAME)
    @ApiModelProperty(value = "Private customer last name", example = "Smith")
    public StringAttribute getLastName() {
        return lastName;
    }

    @JsonProperty(FieldConstant.SOCIAL_SECURITY_NUMBER)
    @ApiModelProperty(value = "The social security number and the formatter code", example = "YYYYMMDD-XXXX")
    public SocialSecurityNumber getSocialSecurityNumber() {
        return socialSecurityNumber;
    }


    public static Builder createBuilder () {
        return new Builder();
    }

    public static final class Builder {
        private StringAttribute firstName;
        private StringAttribute lastName;
        private List<AddressCRM> address;
        private StringAttribute timezone;
        private List<ApprovedTermsConditions> approvedTermsConditions;
        private List<ApprovedMarketInfo> approvedMarketInfo;
        private List<LicensePlate> licensePlates;
        private SocialSecurityNumber socialSecurityNumber;
        private List<PhoneNumber> phoneNumbers;
        private StringAttribute language;
        private List<ApprovedPrivacyPolicy> approvedPrivacyPolicies;

        private Builder(){
            this.address = new ArrayList<>();
            this.approvedTermsConditions = new ArrayList<>();
            this.licensePlates = new ArrayList<>();
            this.phoneNumbers = new ArrayList<>();
        }

        public Builder firstName(final StringAttribute firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final StringAttribute lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder addAddresses(final AddressCRM address){
            this.address.add(address);
            return this;
        }

        public Builder address(final List<AddressCRM> address){
            this.address = address;
            return this;
        }

        public Builder timezone(final StringAttribute timezone){
            this.timezone = timezone;
            return this;
        }

        public Builder addApprovedTermsConditions(final ApprovedTermsConditions approvedTermsConditions){
            this.approvedTermsConditions.add(approvedTermsConditions);
            return this;
        }

        public Builder approvedTermsConditions(final List<ApprovedTermsConditions> approvedTermsConditions){
            this.approvedTermsConditions = approvedTermsConditions;
            return this;
        }

        public Builder approvedMarketInfo(final List<ApprovedMarketInfo> approvedMarketInfo){
            this.approvedMarketInfo = approvedMarketInfo;
            return this;
        }

        public Builder addLicensePlate(final LicensePlate licensePlate){
            this.licensePlates.add(licensePlate);
            return this;
        }

        public Builder licensePlates(final List<LicensePlate> licensePlates){
            this.licensePlates = licensePlates;
            return this;
        }

        public Builder socialSecurityNumber(final SocialSecurityNumber socialSecurityNumber){
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }

        public Builder addPhoneNumber(final PhoneNumber phoneNumber){
            this.phoneNumbers.add(phoneNumber);
            return this;
        }

        public Builder phoneNumbers(final List<PhoneNumber> phoneNumbers){
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public Builder language(final StringAttribute language){
            this.language = language;
            return this;
        }

        public Builder approvedPrivacyPolicies(final List<ApprovedPrivacyPolicy> approvedPrivacyPolicies){
            this.approvedPrivacyPolicies = approvedPrivacyPolicies;
            return this;
        }

        public UpdatePrivateCustomerRequest build(){
            return new UpdatePrivateCustomerRequest(firstName, lastName, address, timezone, approvedTermsConditions, approvedMarketInfo, licensePlates,
                    socialSecurityNumber, phoneNumbers, language, approvedPrivacyPolicies);
        }
    }
}
