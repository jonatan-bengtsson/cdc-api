package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class User {
    private final Long id;
    private final Organization organization;
    private final StringAttribute firstName;
    private final StringAttribute lastName;
    private final String email;
    private final List<Address> address;
    private final StringAttribute timezone;
    private final List<ApprovedAgreement> approvedAgreements;
    private final ApprovedMarketInfo approvedMarketInfo;
    private final List<LicensePlate> licensePlates;
    private final SocialSecurityNumber socialSecurityNumber;
    private final StringAttribute customerNumber;
    private final BooleanAttribute hasChargingAccess;
    private final StringAttribute customerType;
    private final List<PhoneNumber> phoneNumbers;
    private final StringAttribute organizationNumber;
    private final List<StringAttribute> roles;
    private final StringAttribute language;
    private final InstantAttribute activationDate;
    private final InstantAttribute expirationDate;

    public User(Long id, Organization organization, StringAttribute firstName, StringAttribute lastName, String email, List<Address> address, StringAttribute timezone, List<ApprovedAgreement> approvedAgreements, ApprovedMarketInfo approvedMarketInfo, List<LicensePlate> licensePlates, SocialSecurityNumber socialSecurityNumber, StringAttribute customerNumber, BooleanAttribute hasChargingAccess, StringAttribute customerType, List<PhoneNumber> phoneNumbers, StringAttribute organizationNumber, List<Organization> organizationPermissions, List<StringAttribute> roles, StringAttribute language, InstantAttribute activationDate, InstantAttribute expirationDate) {
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
        this.roles = roles;
        this.language = language;
        this.activationDate = activationDate;
        this.expirationDate = expirationDate;
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
        this.roles = null;
        this.language = null;
        this.activationDate = null;
        this.expirationDate = null;
    }


    @JsonProperty("id")
    @ApiModelProperty(position = 1)
    public Long getId() {
        return id;
    }

    @JsonProperty("firstName")
    @ApiModelProperty(position = 2)
    public StringAttribute getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    @ApiModelProperty(position = 3)
    public StringAttribute getLastName() {
        return lastName;
    }

    @JsonProperty("email")
    @ApiModelProperty(position = 4)
    public String getEmail() {
        return email;
    }

    @JsonProperty("address")
    @ApiModelProperty(position = 5)
    public List<Address> getAddress() {
        return address;
    }

    @JsonProperty("timeZone")
    @ApiModelProperty(position = 6)
    public StringAttribute getTimezone() {
        return timezone;
    }

    @JsonProperty("approvedAgreements")
    @ApiModelProperty(position = 7)
    public List<ApprovedAgreement> getApprovedAgreements() {
        return approvedAgreements;
    }

    @JsonProperty("approvedMarketInfo")
    @ApiModelProperty(position = 8)
    public ApprovedMarketInfo getApprovedMarketInfo() {
        return approvedMarketInfo;
    }

    @JsonProperty("licensePlates")
    @ApiModelProperty(position = 9)
    public List<LicensePlate> getLicensePlates() {
        return licensePlates;
    }

    @JsonProperty("socialSecurityNumber")
    @ApiModelProperty(position = 10)
    public SocialSecurityNumber getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @JsonProperty("customerNumber")
    @ApiModelProperty(position = 11)
    public StringAttribute getCustomerNumber() {
        return customerNumber;
    }

    @JsonProperty("hasChargingAccess")
    @ApiModelProperty(position = 12)
    public BooleanAttribute getHasChargingAccess() {
        return hasChargingAccess;
    }

    @JsonProperty("customerType")
    @ApiModelProperty(position = 13)
    public StringAttribute getCustomerType() {
        return customerType;
    }

    @JsonProperty("phoneNumbers")
    @ApiModelProperty(position = 14)
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    @JsonProperty("provider")
    @ApiModelProperty(position = 15)
    public Organization getOrganization() {
        return organization;
    }

    @JsonProperty("organizationNumber")
    @ApiModelProperty(position = 16)
    public StringAttribute getOrganizationNumber() {
        return organizationNumber;
    }

    @JsonProperty("roles")
    @ApiModelProperty(position = 18)
    public List<StringAttribute> getRoles() {
        return roles;
    }

    @JsonProperty("language")
    @ApiModelProperty(position = 19)
    public StringAttribute getLanguage() {
        return language;
    }

    @JsonProperty("activationDate")
    @ApiModelProperty(position = 20)
    public InstantAttribute getActivationDate() {
        return activationDate;
    }

    @JsonProperty("expirationDate")
    @ApiModelProperty(position = 21)
    public InstantAttribute getExpirationDate() {
        return expirationDate;
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
        private List<Address> address;
        private StringAttribute timezone;
        private List<ApprovedAgreement> approvedAgreements;
        private ApprovedMarketInfo approvedMarketInfo;
        private List<LicensePlate> licensePlates;
        private SocialSecurityNumber socialSecurityNumber;
        private StringAttribute customerNumber;
        private BooleanAttribute hasChargingAccess;
        private StringAttribute customerType;
        private List<PhoneNumber> phoneNumbers;
        private StringAttribute organizationNumber;
        private List<Organization> organizationPermissions;
        private List<StringAttribute> roles;
        private StringAttribute language;
        private InstantAttribute activationDate;
        private InstantAttribute expirationDate;

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

        public Builder address(final List<Address> address) {
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

        public Builder approvedMarketInfo(final ApprovedMarketInfo approvedMarketInfo) {
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
        public Builder organizationNumber(final StringAttribute organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder organizationPermissions(final List<Organization> organizationPermissions) {
            this.organizationPermissions = organizationPermissions;
            return this;
        }

        public Builder roles(final List<StringAttribute> roles) {
            this.roles = roles;
            return this;
        }

        public Builder activationDate(final InstantAttribute activationDate) {
            this.activationDate = activationDate;
            return this;
        }

        public Builder expirationDate(final InstantAttribute expirationDate) {
            this.expirationDate = expirationDate;
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


        public User build() {
            return new User(id, organization, firstName, lastName, email, address, timezone, approvedAgreements, approvedMarketInfo, licensePlates,
                    socialSecurityNumber, customerNumber, hasChargingAccess, customerType, phoneNumbers,
                    organizationNumber, organizationPermissions, roles, language,
                    activationDate, expirationDate);
        }
    }

}
