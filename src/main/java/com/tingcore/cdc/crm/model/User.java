package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class User {
    private final Long id;
    private final Organization organization;
    private final StringAttribute firstName;
    private final StringAttribute lastName;
    private final String email;
    private final List<AddressCRM> address;
    private final StringAttribute timezone;
    private final List<ApprovedAgreement> approvedAgreements;
    private final List<ApprovedMarketInfo> approvedMarketInfo;
    private final List<LicensePlate> licensePlates;
    private final SocialSecurityNumber socialSecurityNumber;
    private final StringAttribute customerNumber;
    private final BooleanAttribute hasChargingAccess;
    private final StringAttribute customerType;
    private final List<PhoneNumber> phoneNumbers;
    private final OrganizationNumber organizationNumber;
    private final StringAttribute language;
    private final InstantAttribute activationDate;
    private final List<ApprovedPrivacy> approvedPrivacy;
    private final StringAttribute name;
    private final StringAttribute contactFirstName;
    private final StringAttribute contactLastName;
    private final List<PhoneNumber> contactPhoneNumber;
    private final StringAttribute contactEmail;

    public User(final Long id,
                final Organization organization,
                final StringAttribute firstName,
                final StringAttribute lastName,
                final String email,
                final List<AddressCRM> address,
                final StringAttribute timezone,
                final List<ApprovedAgreement> approvedAgreements,
                final List<ApprovedMarketInfo> approvedMarketInfo,
                final List<LicensePlate> licensePlates,
                final SocialSecurityNumber socialSecurityNumber,
                final StringAttribute customerNumber,
                final BooleanAttribute hasChargingAccess,
                final StringAttribute customerType,
                final List<PhoneNumber> phoneNumbers,
                final OrganizationNumber organizationNumber,
                final StringAttribute language,
                final InstantAttribute activationDate,
                final List<ApprovedPrivacy> approvedPrivacy,
                final StringAttribute name,
                final StringAttribute contactFirstName,
                final StringAttribute contactLastName,
                final List<PhoneNumber> contactPhoneNumber,
                final StringAttribute contactEmail) {
        this.id = id;
        this.organization = organization;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.timezone = timezone;
        this.approvedAgreements = approvedAgreements;
        this.approvedMarketInfo = approvedMarketInfo;
        this.licensePlates = licensePlates;
        this.socialSecurityNumber = socialSecurityNumber;
        this.customerNumber = customerNumber;
        this.hasChargingAccess = hasChargingAccess;
        this.customerType = customerType;
        this.phoneNumbers = phoneNumbers;
        this.organizationNumber = organizationNumber;
        this.language = language;
        this.activationDate = activationDate;
        this.approvedPrivacy = approvedPrivacy;
        this.name = name;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactEmail = contactEmail;
    }

    public User() {
        this.id = null;
        this.organization = null;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.address = null;
        this.timezone = null;
        this.approvedAgreements = null;
        this.approvedMarketInfo = null;
        this.licensePlates = null;
        this.socialSecurityNumber = null;
        this.customerNumber = null;
        this.hasChargingAccess = null;
        this.customerType = null;
        this.phoneNumbers = null;
        this.organizationNumber = null;
        this.language = null;
        this.activationDate = null;
        this.approvedPrivacy = null;
        this.name = null;
        this.contactFirstName = null;
        this.contactLastName = null;
        this.contactEmail = null;
        this.contactPhoneNumber = null;
    }
    
    
    
    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(position = 1)
    public Long getId() {
        return id;
    }
    
    @JsonProperty(FieldConstant.FIRST_NAME)
    @ApiModelProperty(position = 2)
    public StringAttribute getFirstName() {
        return firstName;
    }
    
    @JsonProperty(FieldConstant.LAST_NAME)
    @ApiModelProperty(position = 3)
    public StringAttribute getLastName() {
        return lastName;
    }
    
    @JsonProperty(FieldConstant.EMAIL)
    @ApiModelProperty(position = 4)
    public String getEmail() {
        return email;
    }
    
    @JsonProperty(FieldConstant.ADDRESS)
    @ApiModelProperty(position = 5)
    public List<AddressCRM> getAddress() {
        return address;
    }
    
    @JsonProperty(FieldConstant.TIME_ZONE)
    @ApiModelProperty(position = 6)
    public StringAttribute getTimezone() {
        return timezone;
    }
    
    @JsonProperty(FieldConstant.APPROVED_AGREEMENTS)
    @ApiModelProperty(position = 7)
    public List<ApprovedAgreement> getApprovedAgreements() {
        return approvedAgreements;
    }
    
    @JsonProperty(FieldConstant.APPROVED_MARKET_INFO)
    @ApiModelProperty(position = 8)
    public List<ApprovedMarketInfo> getApprovedMarketInfo() {
        return approvedMarketInfo;
    }
    
    @JsonProperty(FieldConstant.LICENSE_PLATES)
    @ApiModelProperty(position = 9)
    public List<LicensePlate> getLicensePlates() {
        return licensePlates;
    }
    
    @JsonProperty(FieldConstant.SOCIAL_SECURITY_NUMBER)
    @ApiModelProperty(position = 10)
    public SocialSecurityNumber getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    
    @JsonProperty(FieldConstant.CUSTOMER_NUMBER)
    @ApiModelProperty(position = 11)
    public StringAttribute getCustomerNumber() {
        return customerNumber;
    }
    
    @JsonProperty(FieldConstant.HAS_CHARGING_ACCESS)
    @ApiModelProperty(position = 12)
    public BooleanAttribute getHasChargingAccess() {
        return hasChargingAccess;
    }
    
    @JsonProperty(FieldConstant.CUSTOMER_TYPE)
    @ApiModelProperty(position = 13)
    public StringAttribute getCustomerType() {
        return customerType;
    }
    
    @JsonProperty(FieldConstant.PHONE_NUMBERS)
    @ApiModelProperty(position = 14)
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    @JsonProperty(FieldConstant.PROVIDER)
    @ApiModelProperty(position = 15)
    public Organization getOrganization() {
        return organization;
    }
    
    @JsonProperty(FieldConstant.ORGANIZATION_NUMBER)
    @ApiModelProperty(position = 16)
    public OrganizationNumber getOrganizationNumber() {
        return organizationNumber;
    }
    
    @JsonProperty(FieldConstant.LANGUAGE)
    @ApiModelProperty(position = 17)
    public StringAttribute getLanguage() {
        return language;
    }
    
    @JsonProperty(FieldConstant.ACTIVATION_DATE)
    @ApiModelProperty(position = 18)
    public InstantAttribute getActivationDate() {
        return activationDate;
    }

    @JsonProperty(FieldConstant.APPROVED_PRIVACY)
    @ApiModelProperty(position = 20)
    public List<ApprovedPrivacy> getApprovedPrivacy() {
        return approvedPrivacy;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(position = 21)
    public StringAttribute getName() {
        return name;
    }

    @JsonProperty(FieldConstant.CONTACT_FIRST_NAME)
    @ApiModelProperty(position = 22)
    public StringAttribute getContactFirstName() {
        return contactFirstName;
    }

    @JsonProperty(FieldConstant.CONTACT_LAST_NAME)
    @ApiModelProperty(position = 23)
    public StringAttribute getContactLastName() {
        return contactLastName;
    }

    @JsonProperty(FieldConstant.CONTACT_EMAIL)
    @ApiModelProperty(position = 24)
    public StringAttribute getContactEmail() {
        return contactEmail;
    }

    @JsonProperty(FieldConstant.CONTACT_PHONE_NUMBER)
    @ApiModelProperty(position = 25)
    public List<PhoneNumber> getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    
    
    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Organization organization;
        private String email;
        private StringAttribute firstName;
        private StringAttribute lastName;
        private List<AddressCRM> address;
        private StringAttribute timezone;
        private List<ApprovedAgreement> approvedAgreements;
        private List<ApprovedMarketInfo> approvedMarketInfo;
        private List<LicensePlate> licensePlates;
        private SocialSecurityNumber socialSecurityNumber;
        private StringAttribute customerNumber;
        private BooleanAttribute hasChargingAccess;
        private StringAttribute customerType;
        private List<PhoneNumber> phoneNumbers;
        private OrganizationNumber organizationNumber;
        private StringAttribute language;
        private InstantAttribute activationDate;
        private List<ApprovedPrivacy> approvedPrivacy;
        private StringAttribute name;
        private StringAttribute contactFirstName;
        private StringAttribute contactLastName;
        private List<PhoneNumber> contactPhoneNumber;
        private StringAttribute contactEmail;

        Builder() {
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder organization(final Organization organization) {
            this.organization = organization;
            return this;
        }

        public Builder firstName(final StringAttribute firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final StringAttribute lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder address(final List<AddressCRM> address) {
            this.address = address;
            return this;
        }

        public Builder timezone(final StringAttribute timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder approvedAgreements(final List<ApprovedAgreement> approvedAgreements) {
            this.approvedAgreements = approvedAgreements;
            return this;
        }

        public Builder approvedMarketInfo(final List<ApprovedMarketInfo> approvedMarketInfo) {
            this.approvedMarketInfo = approvedMarketInfo;
            return this;
        }

        public Builder licensePlates(final List<LicensePlate> licensePlates) {
            this.licensePlates = licensePlates;
            return this;
        }

        public Builder socialSecurityNumber(final SocialSecurityNumber socialSecurityNumber) {
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }

        public Builder customerNumber(final StringAttribute customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }

        public Builder hasChargingAccess(final BooleanAttribute hasChargingAccess) {
            this.hasChargingAccess = hasChargingAccess;
            return this;
        }

        public Builder phoneNumbers(final List<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        // organizationNumber is the id for a business customer
        public Builder organizationNumber(final OrganizationNumber organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder activationDate(final InstantAttribute activationDate) {
            this.activationDate = activationDate;
            return this;
        }

        public Builder customerType(final StringAttribute customerType) {
            this.customerType = customerType;
            return this;
        }

        public Builder language(final StringAttribute language) {
            this.language = language;
            return this;
        }

        public Builder approvedPrivacy(final List<ApprovedPrivacy> approvedPrivacy) {
            this.approvedPrivacy = approvedPrivacy;
            return this;
        }

        public Builder name(final StringAttribute name) {
            this.name = name;
            return this;
        }

        public Builder contactFirstName(final StringAttribute contactFirstName) {
            this.contactFirstName = contactFirstName;
            return this;
        }

        public Builder contactLastName(final StringAttribute contactLastName) {
            this.contactLastName= contactLastName;
            return this;
        }

        public Builder contactEmail(final StringAttribute contactEmail) {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder contactPhoneNumber(final List<PhoneNumber> contactPhoneNumber) {
            this.contactPhoneNumber = contactPhoneNumber;
            return this;
        }


        public User build() {
            return new User(id, organization, firstName, lastName, email, address, timezone, approvedAgreements, approvedMarketInfo, licensePlates,
                    socialSecurityNumber, customerNumber, hasChargingAccess, customerType, phoneNumbers,
                    organizationNumber, language,
                    activationDate, approvedPrivacy, name, contactFirstName, contactLastName, contactPhoneNumber, contactEmail);
        }
    }

}
