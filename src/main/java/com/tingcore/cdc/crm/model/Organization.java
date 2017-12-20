package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2017-10-30.
 */
@ApiModel
public class Organization {
    private Long id;
    private String name;
    private OrganizationNumber organizationNumber;
    private List<Address> visitingAddresses;
    private StringAttribute email;
    private List<PhoneNumber> phoneNumbers;
    private StringAttribute diagnosticsUploadLink;
    private StringAttribute contactFirstName;
    private StringAttribute contactLastName;
    private List<PhoneNumber> contactPhoneNumber;
    private StringAttribute contactEmail;
    private StringAttribute contactNotes;
    private Vat vat;
    private StringAttribute defaultCurrency;
    private Address billingAddress;
    private List<PhoneNumber> billingTelephone;
    private StringAttribute organizationType;


    public Organization() {
    }
    
    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(position = 1)
    public Long getId () {
        return id;
    }
    
    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(position = 2)
    public String getName() {
        return name;
    }
    
    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    @ApiModelProperty(position = 3)
    public OrganizationNumber getOrganizationNumber() {
        return organizationNumber;
    }
    
    @JsonProperty(FieldConstant.EMAIL)
    @ApiModelProperty(position = 4)
    public StringAttribute getEmail () {
        return email;
    }
    
    @JsonProperty(FieldConstant.PHONE_NUMBERS)
    @ApiModelProperty(position = 5)
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    @JsonProperty(FieldConstant.DIAGNOSTICS_UPLOAD_LINK)
    @ApiModelProperty(position = 6)
    public StringAttribute getDiagnosticsUploadLink () {
        return diagnosticsUploadLink;
    }
    
    @JsonProperty(FieldConstant.CONTACT_FIRST_NAME)
    @ApiModelProperty(position = 7)
    public StringAttribute getContactFirstName() {
        return contactFirstName;
    }
    
    @JsonProperty(FieldConstant.CONTACT_LAST_NAME)
    @ApiModelProperty(position = 8)
    public StringAttribute getContactLastName() {
        return contactLastName;
    }
    
    @JsonProperty(FieldConstant.CONTACT_PHONE_NUMBER)
    @ApiModelProperty(position = 9)
    public List<PhoneNumber> getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    
    @JsonProperty(FieldConstant.CONTACT_EMAIL)
    @ApiModelProperty(position = 10)
    public StringAttribute getContactEmail() {
        return contactEmail;
    }
    
    @JsonProperty(FieldConstant.CONTACT_NOTES)
    @ApiModelProperty(position = 11)
    public StringAttribute getContactNotes() {
        return contactNotes;
    }
    
    @JsonProperty(FieldConstant.VAT)
    @ApiModelProperty(position = 12)
    public Vat getVat() {
        return vat;
    }
    
    @JsonProperty(FieldConstant.DEFAULT_CURRENCY)
    @ApiModelProperty(position = 13)
    public StringAttribute getDefaultCurrency () {
        return defaultCurrency;
    }
    
    @JsonProperty(FieldConstant.BILLING_ADDRESS)
    @ApiModelProperty(position = 14)
    public Address getBillingAddress () {
        return billingAddress;
    }
    
    @JsonProperty(FieldConstant.BILLING_TELEPHONE)
    @ApiModelProperty(position = 15)
    public List<PhoneNumber> getBillingTelephone () {
        return billingTelephone;
    }
    
    @JsonProperty(FieldConstant.VISITING_ADDRESS)
    @ApiModelProperty(position = 16)
    public List<Address> getVisitingAddresses () {
        return visitingAddresses;
    }
    
    @JsonProperty(FieldConstant.ORGANIZATION_TYPE)
    @ApiModelProperty(position = 17)
    public StringAttribute getOrganizationType() {
        return organizationType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizationNumber(OrganizationNumber organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public void setEmail(StringAttribute email) {
        this.email = email;
    }

    public void setPhoneNumbers(List<PhoneNumber> telephone) {
        this.phoneNumbers = telephone;
    }

    public void setDiagnosticsUploadLink(StringAttribute diagnosticsUploadLink) {
        this.diagnosticsUploadLink = diagnosticsUploadLink;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public void setDefaultCurrency(StringAttribute defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setBillingTelephone(List<PhoneNumber> billingTelephone) {
        this.billingTelephone = billingTelephone;
    }

    public void setContactFirstName(StringAttribute contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(StringAttribute contactLastName) {
        this.contactLastName = contactLastName;
    }

    public void setContactPhoneNumber(List<PhoneNumber> contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public void setContactEmail(StringAttribute contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactNotes(StringAttribute contactNotes) {
        this.contactNotes = contactNotes;
    }

    public void setVisitingAddresses(List<Address> visitingAddresses) {
        this.visitingAddresses = visitingAddresses;
    }

    public void setOrganizationType(StringAttribute organizationType) {
        this.organizationType = organizationType;
    }


    public static Builder createBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Long id;
        private String name;
        private OrganizationNumber organizationNumber;
        private List<Address> visitingAddress;
        private StringAttribute email;
        private List<PhoneNumber> phoneNumbers;
        private StringAttribute diagnosticsUploadLink;
        private StringAttribute contactFirstName;
        private StringAttribute contactLastName;
        private List<PhoneNumber> contactPhoneNumber;
        private StringAttribute contactEmail;
        private StringAttribute contactNotes;
        private Vat vat;
        private StringAttribute defaultCurrency;
        private Address billingAddress;
        private List<PhoneNumber> billingTelephone;
        private StringAttribute organizationType;

        private Builder() {
        }


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder organizationNumber(OrganizationNumber organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder visitingAddress(List<Address> visitingAddress) {
            this.visitingAddress = visitingAddress;
            return this;
        }

        public Builder email(StringAttribute email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(List<PhoneNumber> telephone) {
            this.phoneNumbers = telephone;
            return this;
        }

        public Builder diagnosticsUploadLink(StringAttribute diagnosticsUploadLink) {
            this.diagnosticsUploadLink = diagnosticsUploadLink;
            return this;
        }

        public Builder contactFirstName(StringAttribute contactFirstName) {
            this.contactFirstName = contactFirstName;
            return this;
        }

        public Builder contactLastName(StringAttribute contactLastName) {
            this.contactLastName = contactLastName;
            return this;
        }

        public Builder contactPhoneNumber(List<PhoneNumber> contactPhoneNumber) {
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }

        public Builder contactEmail(StringAttribute contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder contactNotes(StringAttribute contactNotes) {
            this.contactNotes = contactNotes;
            return this;
        }

        public Builder vat(Vat vat) {
            this.vat = vat;
            return this;
        }

        public Builder defaultCurrency(StringAttribute defaultCurrency) {
            this.defaultCurrency = defaultCurrency;
            return this;
        }

        public Builder billingAddress(Address billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder billingTelephone(List<PhoneNumber> billingTelephone) {
            this.billingTelephone = billingTelephone;
            return this;
        }

        public Builder organizationType(StringAttribute organizationType) {
            this.organizationType = organizationType;
            return this;
        }

        public Organization build() {
            Organization organization = new Organization();
            organization.setId(id);
            organization.setName(name);
            organization.setOrganizationNumber(organizationNumber);
            organization.setVisitingAddresses(visitingAddress);
            organization.setEmail(email);
            organization.setPhoneNumbers(phoneNumbers);
            organization.setDiagnosticsUploadLink(diagnosticsUploadLink);
            organization.setContactFirstName(contactFirstName);
            organization.setContactLastName(contactLastName);
            organization.setContactPhoneNumber(contactPhoneNumber);
            organization.setContactEmail(contactEmail);
            organization.setContactNotes(contactNotes);
            organization.setVat(vat);
            organization.setDefaultCurrency(defaultCurrency);
            organization.setBillingAddress(billingAddress);
            organization.setBillingTelephone(billingTelephone);
            organization.setOrganizationType(organizationType);
            return organization;
        }
    }

}
