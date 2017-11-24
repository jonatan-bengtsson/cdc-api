package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Address> address;
    private BaseAttribute timezone;
    private List<ApprovedTerms> approvedTerms;
    private BaseAttribute approvedMarketInfo;
    private List<LicensePlate> licensePlates;
    private BaseAttribute socialSecurityNumber;
    private BaseAttribute customerNumber;
    private BaseAttribute isLockedOut;
    private BaseAttribute customerType;
    private List<PhoneNumber> phoneNumbers;
    private BaseAttribute provider;
    private BaseAttribute organizationNumber;
    private List<BaseAttribute> connectedOrganizations;
    private List<BaseAttribute> roles;
    private BaseAttribute language;
    private BaseAttribute activationDate;
    private BaseAttribute expirationDate;

    public User(Long id, String firstName, String lastName, String email, List<Address> address, BaseAttribute timezone, List<ApprovedTerms> approvedTerms, BaseAttribute approvedMarketInfo, List<LicensePlate> licensePlates, BaseAttribute socialSecurityNumber, BaseAttribute customerNumber, BaseAttribute isLockedOut, BaseAttribute customerType, List<PhoneNumber> phoneNumbers, BaseAttribute provider, BaseAttribute organizationNumber, List<BaseAttribute> connectedOrganizations, List<BaseAttribute> roles, BaseAttribute language, BaseAttribute activationDate, BaseAttribute expirationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.timezone = timezone;
        this.approvedTerms = approvedTerms;
        this.approvedMarketInfo = approvedMarketInfo;
        this.licensePlates = licensePlates;
        this.socialSecurityNumber = socialSecurityNumber;
        this.customerNumber = customerNumber;
        this.isLockedOut = isLockedOut;
        this.customerType = customerType;
        this.phoneNumbers = phoneNumbers;
        this.provider = provider;
        this.organizationNumber = organizationNumber;
        this.connectedOrganizations = connectedOrganizations;
        this.roles = roles;
        this.language = language;
        this.activationDate = activationDate;
        this.expirationDate = expirationDate;
    }

    public User() {
    }

    @JsonProperty("id")
    @ApiModelProperty(position = 1)
    public Long getId() {
        return id;
    }

    @JsonProperty("firstName")
    @ApiModelProperty(position = 2)
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    @ApiModelProperty(position = 3)
    public String getLastName() {
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
    public BaseAttribute getTimezone() {
        return timezone;
    }

    @JsonProperty("approvedTerms")
    @ApiModelProperty(position = 7)
    public List<ApprovedTerms> getApprovedTerms() {
        return approvedTerms;
    }

    @JsonProperty("approvedMarketInfo")
    @ApiModelProperty(position = 8)
    public BaseAttribute getApprovedMarketInfo() {
        return approvedMarketInfo;
    }

    @JsonProperty("licensePlates")
    @ApiModelProperty(position = 9)
    public List<LicensePlate> getLicensePlates() {
        return licensePlates;
    }

    @JsonProperty("socialSecurityNumber")
    @ApiModelProperty(position = 10)
    public BaseAttribute getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @JsonProperty("customerNumber")
    @ApiModelProperty(position = 11)
    public BaseAttribute getCustomerNumber() {
        return customerNumber;
    }

    @JsonProperty("lockedOut")
    @ApiModelProperty(position = 12)
    public BaseAttribute getIsLockedOut() {
        return isLockedOut;
    }

    @JsonProperty("customerType")
    @ApiModelProperty(position = 13)
    public BaseAttribute getCustomerType() {
        return customerType;
    }

    @JsonProperty("phoneNumbers")
    @ApiModelProperty(position = 14)
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    @JsonProperty("provider")
    @ApiModelProperty(position = 15)
    public BaseAttribute getProvider() {
        return provider;
    }

    @JsonProperty("organizationNumber")
    @ApiModelProperty(position = 16)
    public BaseAttribute getOrganizationNumber() {
        return organizationNumber;
    }

    @JsonProperty("connectedOrganizations")
    @ApiModelProperty(position = 17)
    public List<BaseAttribute> getConnectedOrganizations() {
        return connectedOrganizations;
    }

    @JsonProperty("roles")
    @ApiModelProperty(position = 18)
    public List<BaseAttribute> getRoles() {
        return roles;
    }

    @JsonProperty("language")
    @ApiModelProperty(position = 19)
    public BaseAttribute getLanguage() {
        return language;
    }

    @JsonProperty("activationDate")
    @ApiModelProperty(position = 20)
    public BaseAttribute getActivationDate() {
        return activationDate;
    }

    @JsonProperty("expirationDate")
    @ApiModelProperty(position = 21)
    public BaseAttribute getExpirationDate() {
        return expirationDate;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id = null;
        private String firstName = null;
        private String lastName = null;
        private String email = null;
        private List<Address> address = null;
        private BaseAttribute timezone = null;
        private List<ApprovedTerms> approvedTerms = null;
        private BaseAttribute approvedMarketInfo = null;
        private List<LicensePlate> licensePlates = null;
        private BaseAttribute socialSecurityNumber = null;
        private BaseAttribute customerNumber = null;
        private BaseAttribute isLockedOut = null;
        private BaseAttribute customerType = null;
        private List<PhoneNumber> phoneNumbers = null;
        private BaseAttribute provider = null;
        private BaseAttribute organizationNumber = null;
        private List<BaseAttribute> connectedOrganizations = null;
        private List<BaseAttribute> roles = null;
        private BaseAttribute language = null;
        private BaseAttribute activationDate = null;
        private BaseAttribute expirationDate = null;

        Builder() {
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
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

        public Builder timezone(final BaseAttribute timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder approvedTerms(final List<ApprovedTerms> approvedTerms) {
            this.approvedTerms = approvedTerms;
            return this;
        }

        public Builder approvedMarketInfo(final BaseAttribute approvedMarketInfo) {
            this.approvedMarketInfo = approvedMarketInfo;
            return this;
        }

        public Builder licensePlates(final List<LicensePlate> licensePlates) {
            this.licensePlates = licensePlates;
            return this;
        }

        public Builder socialSecurityNumber(final BaseAttribute socialSecurityNumber) {
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }

        public Builder customerNumber(final BaseAttribute customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }

        public Builder isLockedOut(final BaseAttribute isLockedOut) {
            this.isLockedOut = isLockedOut;
            return this;
        }

        public Builder phoneNumbers(final List<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }


        // provider is the organization a customer is tied to
        public Builder provider(final BaseAttribute provider) {
            this.provider = provider;
            return this;
        }

        // organizationNumber is the id for a business customer
        public Builder organizationNumber(final BaseAttribute organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }

        public Builder connectedOrganizations(final List<BaseAttribute> connectedOrganizations) {
            this.connectedOrganizations = connectedOrganizations;
            return this;
        }

        public Builder roles(final List<BaseAttribute> roles) {
            this.roles = roles;
            return this;
        }

        public Builder activationDate(final BaseAttribute activationDate) {
            this.activationDate = activationDate;
            return this;
        }

        public Builder expirationDate(final BaseAttribute expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder customerType(final BaseAttribute customerType) {
            this.customerType = customerType;
            return this;
        }

        public Builder language(final BaseAttribute language) {
            this.language = language;
            return this;
        }


        public User build() {
            return new User(id, firstName, lastName, email, address, timezone, approvedTerms, approvedMarketInfo, licensePlates,
                    socialSecurityNumber, customerNumber, isLockedOut, customerType, phoneNumbers,
                    provider, organizationNumber, connectedOrganizations, roles, language,
                    activationDate, expirationDate);
        }
    }

}
