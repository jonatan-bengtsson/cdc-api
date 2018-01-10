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
public class UpdateUserRequest {
    private StringAttribute firstName;
    private StringAttribute lastName;
    private StringAttribute name;
    private List<AddressCRM> address;
    private StringAttribute timezone;
    private List<ApprovedAgreement> approvedAgreements;
    private ApprovedMarketInfo approvedMarketInfo;
    private List<LicensePlate> licensePlates;
    private SocialSecurityNumber socialSecurityNumber;
    private StringAttribute customerType;
    private List<PhoneNumber> phoneNumbers;
    private OrganizationNumber organizationNumber;
    private StringAttribute language;
    private ApprovedPrivacy approvedPrivacy;

    public UpdateUserRequest (StringAttribute firstName, StringAttribute lastName, StringAttribute name, List<AddressCRM> address, StringAttribute timezone, List<ApprovedAgreement> approvedAgreements, ApprovedMarketInfo approvedMarketInfo, List<LicensePlate> licensePlates, SocialSecurityNumber socialSecurityNumber, StringAttribute customerType, List<PhoneNumber> phoneNumbers, OrganizationNumber organizationNumber, StringAttribute language, ApprovedPrivacy approvedPrivacy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.address = address;
        this.timezone = timezone;
        this.approvedAgreements = approvedAgreements;
        this.approvedMarketInfo = approvedMarketInfo;
        this.licensePlates = licensePlates;
        this.socialSecurityNumber = socialSecurityNumber;
        this.customerType = customerType;
        this.phoneNumbers = phoneNumbers;
        this.organizationNumber = organizationNumber;
        this.language = language;
        this.approvedPrivacy = approvedPrivacy;
    }

    public UpdateUserRequest () {
    }

    @JsonProperty(FieldConstant.FIRST_NAME)
    @ApiModelProperty(value = "Private customer first name", example = "Anna")
    public StringAttribute getFirstName() {
        return firstName;
    }

    @JsonProperty(FieldConstant.LAST_NAME)
    @ApiModelProperty(value = "Private customer Last name")
    public StringAttribute getLastName() {
        return lastName;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(value = "Business customer name", example = "Company name")
    public StringAttribute getName() {
        return name;
    }

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

    @JsonProperty(FieldConstant.SOCIAL_SECURITY_NUMBER)
    @ApiModelProperty(value = "The social security number and the formatter code", example = "YYYYMMDD-XXXX")
    public SocialSecurityNumber getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @JsonProperty(FieldConstant.CUSTOMER_TYPE)
    @ApiModelProperty(value = "The type of custoner", example = "privateCustomer")
    public StringAttribute getCustomerType() {
        return customerType;
    }

    @JsonProperty(FieldConstant.PHONE_NUMBERS)
    @ApiModelProperty(value = "The phoneNumber for a customer")
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    @ApiModelProperty(value = "The organization number for a business customer", example = "XXXXXX-XXXX")
    public OrganizationNumber getOrganizationNumber() {
        return organizationNumber;
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

    public static Builder createBuilder () {
        return new Builder();
    }

    public static final class Builder {
        private StringAttribute firstName;
        private StringAttribute lastName;
        private StringAttribute name;
        private List<AddressCRM> address;
        private StringAttribute timezone;
        private List<ApprovedAgreement> approvedAgreements;
        private ApprovedMarketInfo approvedMarketInfo;
        private List<LicensePlate> licensePlates;
        private SocialSecurityNumber socialSecurityNumber;
        private StringAttribute customerType;
        private List<PhoneNumber> phoneNumbers;
        private OrganizationNumber organizationNumber;
        private StringAttribute language;
        private ApprovedPrivacy approvedPrivacy;

        private Builder(){
            this.address = new ArrayList<>();
            this.approvedAgreements = new ArrayList<>();
            this.licensePlates = new ArrayList<>();
            this.phoneNumbers = new ArrayList<>();
        }

        public Builder firstName(final StringAttribute firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final StringAttribute lastName){
            this.firstName = lastName;
            return this;
        }

        public Builder name(final StringAttribute name){
            this.firstName = name;
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

        public Builder addApprovedAgreement(final ApprovedAgreement approvedAgreement){
            this.approvedAgreements.add(approvedAgreement);
            return this;
        }

        public Builder approvedAgreements(final List<ApprovedAgreement> agreements){
            this.approvedAgreements = agreements;
            return this;
        }

        public Builder approvedMarketInfo(final ApprovedMarketInfo approvedMarketInfo){
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

        public Builder customerType(final StringAttribute customerType){
            this.customerType = customerType;
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

        public Builder organizationNumber(final OrganizationNumber organizationNumber){
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder language(final StringAttribute language){
            this.language = language;
            return this;
        }

        public Builder approvedPrivacy(final ApprovedPrivacy approvedPrivacy){
            this.approvedPrivacy = approvedPrivacy;
            return this;
        }

        public UpdateUserRequest build(){
            return new UpdateUserRequest(firstName, lastName, name, address, timezone, approvedAgreements, approvedMarketInfo, licensePlates,
                    socialSecurityNumber, customerType, phoneNumbers, organizationNumber, language, approvedPrivacy);
        }
    }
}
