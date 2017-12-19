package com.tingcore.cdc.crm.response;

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
public class GetOrganizationResponse {
    private Long id;
    private String name;
    private OrganizationNumberResponse organizationNumber;
    private List<AddressResponse> visitingAddresses;
    private StringAttributeResponse email;
    private List<PhoneNumberResponse> phoneNumbers;
    private StringAttributeResponse diagnosticsUploadLink;
    private StringAttributeResponse contactFirstName;
    private StringAttributeResponse contactLastName;
    private List<PhoneNumberResponse> contactPhoneNumber;
    private StringAttributeResponse contactEmail;
    private StringAttributeResponse contactNotes;
    private VatResponse vat;
    private StringAttributeResponse defaultCurrency;
    private AddressResponse billingAddress;
    private List<PhoneNumberResponse> billingTelephone;
    private StringAttributeResponse organizationType;


    public GetOrganizationResponse() {
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
    public OrganizationNumberResponse getOrganizationNumber() {
        return organizationNumber;
    }
    
    @JsonProperty(FieldConstant.EMAIL)
    @ApiModelProperty(position = 4)
    public StringAttributeResponse getEmail () {
        return email;
    }
    
    @JsonProperty(FieldConstant.PHONE_NUMBERS)
    @ApiModelProperty(position = 5)
    public List<PhoneNumberResponse> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    @JsonProperty(FieldConstant.DIAGNOSTICS_UPLOAD_LINK)
    @ApiModelProperty(position = 6)
    public StringAttributeResponse getDiagnosticsUploadLink () {
        return diagnosticsUploadLink;
    }
    
    @JsonProperty(FieldConstant.CONTACT_FIRST_NAME)
    @ApiModelProperty(position = 7)
    public StringAttributeResponse getContactFirstName() {
        return contactFirstName;
    }
    
    @JsonProperty(FieldConstant.CONTACT_LAST_NAME)
    @ApiModelProperty(position = 8)
    public StringAttributeResponse getContactLastName() {
        return contactLastName;
    }
    
    @JsonProperty(FieldConstant.CONTACT_PHONE_NUMBER)
    @ApiModelProperty(position = 9)
    public List<PhoneNumberResponse> getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    
    @JsonProperty(FieldConstant.CONTACT_EMAIL)
    @ApiModelProperty(position = 10)
    public StringAttributeResponse getContactEmail() {
        return contactEmail;
    }
    
    @JsonProperty(FieldConstant.CONTACT_NOTES)
    @ApiModelProperty(position = 11)
    public StringAttributeResponse getContactNotes() {
        return contactNotes;
    }
    
    @JsonProperty(FieldConstant.VAT)
    @ApiModelProperty(position = 12)
    public VatResponse getVat() {
        return vat;
    }
    
    @JsonProperty(FieldConstant.DEFAULT_CURRENCY)
    @ApiModelProperty(position = 13)
    public StringAttributeResponse getDefaultCurrency () {
        return defaultCurrency;
    }
    
    @JsonProperty(FieldConstant.BILLING_ADDRESS)
    @ApiModelProperty(position = 14)
    public AddressResponse getBillingAddress () {
        return billingAddress;
    }
    
    @JsonProperty(FieldConstant.BILLING_TELEPHONE)
    @ApiModelProperty(position = 15)
    public List<PhoneNumberResponse> getBillingTelephone () {
        return billingTelephone;
    }
    
    @JsonProperty(FieldConstant.VISITING_ADDRESS)
    @ApiModelProperty(position = 16)
    public List<AddressResponse> getVisitingAddresses () {
        return visitingAddresses;
    }
    
    @JsonProperty(FieldConstant.ORGANIZATION_TYPE)
    @ApiModelProperty(position = 17)
    public StringAttributeResponse getOrganizationType() {
        return organizationType;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizationNumber(OrganizationNumberResponse organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public void setEmail(StringAttributeResponse email) {
        this.email = email;
    }

    public void setPhoneNumbers(List<PhoneNumberResponse> telephone) {
        this.phoneNumbers = telephone;
    }

    public void setDiagnosticsUploadLink(StringAttributeResponse diagnosticsUploadLink) {
        this.diagnosticsUploadLink = diagnosticsUploadLink;
    }

    public void setVat(VatResponse vat) {
        this.vat = vat;
    }

    public void setDefaultCurrency(StringAttributeResponse defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void setBillingAddress(AddressResponse billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setBillingTelephone(List<PhoneNumberResponse> billingTelephone) {
        this.billingTelephone = billingTelephone;
    }

    public void setContactFirstName(StringAttributeResponse contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(StringAttributeResponse contactLastName) {
        this.contactLastName = contactLastName;
    }

    public void setContactPhoneNumber(List<PhoneNumberResponse> contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public void setContactEmail(StringAttributeResponse contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactNotes(StringAttributeResponse contactNotes) {
        this.contactNotes = contactNotes;
    }

    public void setVisitingAddresses(List<AddressResponse> visitingAddresses) {
        this.visitingAddresses = visitingAddresses;
    }

    public void setOrganizationType(StringAttributeResponse organizationType) {
        this.organizationType = organizationType;
    }


    public static Builder createBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Long id;
        private String name;
        private OrganizationNumberResponse organizationNumber;
        private List<AddressResponse> visitingAddress;
        private StringAttributeResponse email;
        private List<PhoneNumberResponse> phoneNumbers;
        private StringAttributeResponse diagnosticsUploadLink;
        private StringAttributeResponse contactFirstName;
        private StringAttributeResponse contactLastName;
        private List<PhoneNumberResponse> contactPhoneNumber;
        private StringAttributeResponse contactEmail;
        private StringAttributeResponse contactNotes;
        private VatResponse vat;
        private StringAttributeResponse defaultCurrency;
        private AddressResponse billingAddress;
        private List<PhoneNumberResponse> billingTelephone;
        private StringAttributeResponse organizationType;

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

        public Builder organizationNumber(OrganizationNumberResponse organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder visitingAddress(List<AddressResponse> visitingAddress) {
            this.visitingAddress = visitingAddress;
            return this;
        }

        public Builder email(StringAttributeResponse email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(List<PhoneNumberResponse> telephone) {
            this.phoneNumbers = telephone;
            return this;
        }

        public Builder diagnosticsUploadLink(StringAttributeResponse diagnosticsUploadLink) {
            this.diagnosticsUploadLink = diagnosticsUploadLink;
            return this;
        }

        public Builder contactFirstName(StringAttributeResponse contactFirstName) {
            this.contactFirstName = contactFirstName;
            return this;
        }

        public Builder contactLastName(StringAttributeResponse contactLastName) {
            this.contactLastName = contactLastName;
            return this;
        }

        public Builder contactPhoneNumber(List<PhoneNumberResponse> contactPhoneNumber) {
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }

        public Builder contactEmail(StringAttributeResponse contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder contactNotes(StringAttributeResponse contactNotes) {
            this.contactNotes = contactNotes;
            return this;
        }

        public Builder vat(VatResponse vat) {
            this.vat = vat;
            return this;
        }

        public Builder defaultCurrency(StringAttributeResponse defaultCurrency) {
            this.defaultCurrency = defaultCurrency;
            return this;
        }

        public Builder billingAddress(AddressResponse billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder billingTelephone(List<PhoneNumberResponse> billingTelephone) {
            this.billingTelephone = billingTelephone;
            return this;
        }

        public Builder organizationType(StringAttributeResponse organizationType) {
            this.organizationType = organizationType;
            return this;
        }

        public GetOrganizationResponse build() {
            GetOrganizationResponse getOrganizationResponse = new GetOrganizationResponse();
            getOrganizationResponse.setId(id);
            getOrganizationResponse.setName(name);
            getOrganizationResponse.setOrganizationNumber(organizationNumber);
            getOrganizationResponse.setVisitingAddresses(visitingAddress);
            getOrganizationResponse.setEmail(email);
            getOrganizationResponse.setPhoneNumbers(phoneNumbers);
            getOrganizationResponse.setDiagnosticsUploadLink(diagnosticsUploadLink);
            getOrganizationResponse.setContactFirstName(contactFirstName);
            getOrganizationResponse.setContactLastName(contactLastName);
            getOrganizationResponse.setContactPhoneNumber(contactPhoneNumber);
            getOrganizationResponse.setContactEmail(contactEmail);
            getOrganizationResponse.setContactNotes(contactNotes);
            getOrganizationResponse.setVat(vat);
            getOrganizationResponse.setDefaultCurrency(defaultCurrency);
            getOrganizationResponse.setBillingAddress(billingAddress);
            getOrganizationResponse.setBillingTelephone(billingTelephone);
            getOrganizationResponse.setOrganizationType(organizationType);
            return getOrganizationResponse;
        }
    }

}
