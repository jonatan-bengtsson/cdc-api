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
public class UpdateBusinessCustomerRequest extends BaseUpdateCustomerRequest {
    private StringAttribute name;
    private OrganizationNumber organizationNumber;
    private StringAttribute contactFirstName;
    private StringAttribute contactLastName;
    private List<PhoneNumber> contactPhoneNumber;
    private StringAttribute contactEmail;

    public UpdateBusinessCustomerRequest(StringAttribute name, List<AddressCRM> address, StringAttribute timezone, List<ApprovedAgreement> approvedAgreements, ApprovedMarketInfo approvedMarketInfo, List<LicensePlate> licensePlates, List<PhoneNumber> phoneNumbers, OrganizationNumber organizationNumber, StringAttribute language, ApprovedPrivacy approvedPrivacy, StringAttribute contactFirstName, StringAttribute contactLastName, List<PhoneNumber> contactPhoneNumber, StringAttribute contactEmail) {
        this.name = name;
        this.address = address;
        this.timezone = timezone;
        this.approvedAgreements = approvedAgreements;
        this.approvedMarketInfo = approvedMarketInfo;
        this.licensePlates = licensePlates;
        this.phoneNumbers = phoneNumbers;
        this.organizationNumber = organizationNumber;
        this.language = language;
        this.approvedPrivacy = approvedPrivacy;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
    }

    public UpdateBusinessCustomerRequest() {
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(value = "Business customer name", example = "Company name")
    public StringAttribute getName() {
        return name;
    }

    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    @ApiModelProperty(value = "The organization number for a business customer", example = "XXXXXX-XXXX")
    public OrganizationNumber getOrganizationNumber() {
        return organizationNumber;
    }

    @JsonProperty(FieldConstant.CONTACT_FIRST_NAME)
    @ApiModelProperty(value = "The id of the approved privacy agreement")
    public StringAttribute getContactFirstName() {
        return contactFirstName;
    }

    @JsonProperty(FieldConstant.CONTACT_LAST_NAME)
    @ApiModelProperty(value = "The id of the approved privacy agreement")
    public StringAttribute getContactLastName() {
        return contactLastName;
    }

    @JsonProperty(FieldConstant.CONTACT_PHONE_NUMBER)
    @ApiModelProperty(value = "The id of the approved privacy agreement")
    public List<PhoneNumber> getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    @JsonProperty(FieldConstant.CONTACT_EMAIL)
    @ApiModelProperty(value = "The id of the approved privacy agreement")
    public StringAttribute getContactEmail() {
        return contactEmail;
    }



    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private StringAttribute name;
        private List<AddressCRM> address;
        private StringAttribute timezone;
        private List<ApprovedAgreement> approvedAgreements;
        private ApprovedMarketInfo approvedMarketInfo;
        private List<LicensePlate> licensePlates;
        private List<PhoneNumber> phoneNumbers;
        private OrganizationNumber organizationNumber;
        private StringAttribute language;
        private ApprovedPrivacy approvedPrivacy;
        private StringAttribute contactFirstName;
        private StringAttribute contactLastName;
        private List<PhoneNumber> contactPhoneNumber;
        private StringAttribute contactEmail;

        private Builder(){
            this.address = new ArrayList<>();
            this.approvedAgreements = new ArrayList<>();
            this.licensePlates = new ArrayList<>();
            this.phoneNumbers = new ArrayList<>();
            this.contactPhoneNumber = new ArrayList<>();
        }

        public Builder name(final StringAttribute name){
            this.name = name;
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

        public Builder contactFirstName(final StringAttribute contactFirstName){
            this.contactFirstName = contactFirstName;
            return this;
        }

        public Builder contactLastName(final StringAttribute contactLastName){
            this.contactLastName = contactLastName;
            return this;
        }

        public Builder contactPhoneNumber(final List<PhoneNumber> contactPhoneNumber){
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }

        public Builder contactEmail(final StringAttribute contactEmail){
            this.contactEmail = contactEmail;
            return this;
        }


        public UpdateBusinessCustomerRequest build(){
            return new UpdateBusinessCustomerRequest(name, address, timezone, approvedAgreements, approvedMarketInfo, licensePlates,
                    phoneNumbers, organizationNumber, language, approvedPrivacy, contactFirstName, contactLastName, contactPhoneNumber, contactEmail);
        }
    }
}
