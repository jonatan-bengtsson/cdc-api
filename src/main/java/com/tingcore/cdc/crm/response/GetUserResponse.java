package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class GetUserResponse {
    private final Long id;
    private final GetOrganizationResponse organization;
    private final StringAttributeResponse firstName;
    private final StringAttributeResponse lastName;
    private final String email;
    private final List<AddressResponse> address;
    private final StringAttributeResponse timezone;
    private final List<ApprovedAgreementResponse> approvedAgreements;
    private final ApprovedMarketInfoResponse approvedMarketInfo;
    private final List<LicensePlateResponse> licensePlates;
    private final SocialSecurityNumberResponse socialSecurityNumber;
    private final StringAttributeResponse customerNumber;
    private final BooleanAttributeResponse hasChargingAccess;
    private final StringAttributeResponse customerType;
    private final List<PhoneNumberResponse> phoneNumbers;
    private final StringAttributeResponse organizationNumber;
    private final List<StringAttributeResponse> roles;
    private final StringAttributeResponse language;
    private final InstantAttributeResponse activationDate;
    private final InstantAttributeResponse expirationDate;

    public GetUserResponse(Long id, GetOrganizationResponse organization, StringAttributeResponse firstName, StringAttributeResponse lastName, String email, List<AddressResponse> address, StringAttributeResponse timezone, List<ApprovedAgreementResponse> approvedAgreements, ApprovedMarketInfoResponse approvedMarketInfo, List<LicensePlateResponse> licensePlates, SocialSecurityNumberResponse socialSecurityNumber, StringAttributeResponse customerNumber, BooleanAttributeResponse hasChargingAccess, StringAttributeResponse customerType, List<PhoneNumberResponse> phoneNumbers, StringAttributeResponse organizationNumber, List<GetOrganizationResponse> organizationPermissions, List<StringAttributeResponse> roles, StringAttributeResponse language, InstantAttributeResponse activationDate, InstantAttributeResponse expirationDate) {
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

    public GetUserResponse() {
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
    public StringAttributeResponse getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    @ApiModelProperty(position = 3)
    public StringAttributeResponse getLastName() {
        return lastName;
    }
    
    @JsonProperty("email")
    @ApiModelProperty(position = 4)
    public String getEmail() {
        return email;
    }
    
    @JsonProperty("address")
    @ApiModelProperty(position = 5)
    public List<AddressResponse> getAddress() {
        return address;
    }
    
    @JsonProperty("timeZone")
    @ApiModelProperty(position = 6)
    public StringAttributeResponse getTimezone() {
        return timezone;
    }

    @JsonProperty("approvedAgreements")
    @ApiModelProperty(position = 7)
    public List<ApprovedAgreementResponse> getApprovedAgreements() {
        return approvedAgreements;
    }
    
    @JsonProperty("approvedMarketInfo")
    @ApiModelProperty(position = 8)
    public ApprovedMarketInfoResponse getApprovedMarketInfo() {
        return approvedMarketInfo;
    }
    
    @JsonProperty("licensePlates")
    @ApiModelProperty(position = 9)
    public List<LicensePlateResponse> getLicensePlates() {
        return licensePlates;
    }
    
    @JsonProperty("socialSecurityNumber")
    @ApiModelProperty(position = 10)
    public SocialSecurityNumberResponse getSocialSecurityNumber() {
        return socialSecurityNumber;
    }
    
    @JsonProperty("customerNumber")
    @ApiModelProperty(position = 11)
    public StringAttributeResponse getCustomerNumber() {
        return customerNumber;
    }
    
    @JsonProperty("hasChargingAccess")
    @ApiModelProperty(position = 12)
    public BooleanAttributeResponse getHasChargingAccess() {
        return hasChargingAccess;
    }
    
    @JsonProperty("customerType")
    @ApiModelProperty(position = 13)
    public StringAttributeResponse getCustomerType() {
        return customerType;
    }
    
    @JsonProperty("phoneNumbers")
    @ApiModelProperty(position = 14)
    public List<PhoneNumberResponse> getPhoneNumbers() {
        return phoneNumbers;
    }
    
    @JsonProperty("provider")
    @ApiModelProperty(position = 15)
    public GetOrganizationResponse getOrganization() {
        return organization;
    }
    
    @JsonProperty("organizationNumber")
    @ApiModelProperty(position = 16)
    public StringAttributeResponse getOrganizationNumber() {
        return organizationNumber;
    }

    @JsonProperty("roles")
    @ApiModelProperty(position = 18)
    public List<StringAttributeResponse> getRoles() {
        return roles;
    }
    
    @JsonProperty("language")
    @ApiModelProperty(position = 19)
    public StringAttributeResponse getLanguage() {
        return language;
    }
    
    @JsonProperty("activationDate")
    @ApiModelProperty(position = 20)
    public InstantAttributeResponse getActivationDate() {
        return activationDate;
    }
    
    @JsonProperty("expirationDate")
    @ApiModelProperty(position = 21)
    public InstantAttributeResponse getExpirationDate() {
        return expirationDate;
    }
    
    public static Builder createBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        private Long id;
        private GetOrganizationResponse organization;
        private String email;
        private StringAttributeResponse firstName;
        private StringAttributeResponse lastName;
        private List<AddressResponse> address;
        private StringAttributeResponse timezone;
        private List<ApprovedAgreementResponse> approvedAgreements;
        private ApprovedMarketInfoResponse approvedMarketInfo;
        private List<LicensePlateResponse> licensePlates;
        private SocialSecurityNumberResponse socialSecurityNumber;
        private StringAttributeResponse customerNumber;
        private BooleanAttributeResponse hasChargingAccess;
        private StringAttributeResponse customerType;
        private List<PhoneNumberResponse> phoneNumbers;
        private StringAttributeResponse organizationNumber;
        private List<GetOrganizationResponse> organizationPermissions;
        private List<StringAttributeResponse> roles;
        private StringAttributeResponse language;
        private InstantAttributeResponse activationDate;
        private InstantAttributeResponse expirationDate;
        
        Builder() {
        }
        
        public Builder id(final Long id) {
            this.id = id;
            return this;
        }
        
        public Builder organization(final GetOrganizationResponse organization) {
            this.organization = organization;
            return this;
        }


        public Builder firstName(final StringAttributeResponse firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final StringAttributeResponse lastName) {
            this.lastName = lastName;
            return this;
        }
        
        public Builder email(final String email) {
            this.email = email;
            return this;
        }
        
        public Builder address(final List<AddressResponse> address) {
            this.address = address;
            return this;
        }
        
        public Builder timezone(final StringAttributeResponse timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder approvedAgreements(final List<ApprovedAgreementResponse> approvedAgreements) {
            this.approvedAgreements = approvedAgreements;
            return this;
        }
        
        public Builder approvedMarketInfo(final ApprovedMarketInfoResponse approvedMarketInfo) {
            this.approvedMarketInfo = approvedMarketInfo;
            return this;
        }
        
        public Builder licensePlates(final List<LicensePlateResponse> licensePlates) {
            this.licensePlates = licensePlates;
            return this;
        }
        
        public Builder socialSecurityNumber(final SocialSecurityNumberResponse socialSecurityNumber) {
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }
        
        public Builder customerNumber(final StringAttributeResponse customerNumber) {
            this.customerNumber = customerNumber;
            return this;
        }
        
        public Builder hasChargingAccess(final BooleanAttributeResponse hasChargingAccess) {
            this.hasChargingAccess = hasChargingAccess;
            return this;
        }
        
        public Builder phoneNumbers(final List<PhoneNumberResponse> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }
        
        // organizationNumber is the id for a business customer
        public Builder organizationNumber(final StringAttributeResponse organizationNumber) {
            this.organizationNumber = organizationNumber;
            return this;
        }
        
        public Builder organizationPermissions(final List<GetOrganizationResponse> organizationPermissions) {
            this.organizationPermissions = organizationPermissions;
            return this;
        }
        
        public Builder roles(final List<StringAttributeResponse> roles) {
            this.roles = roles;
            return this;
        }
        
        public Builder activationDate(final InstantAttributeResponse activationDate) {
            this.activationDate = activationDate;
            return this;
        }
        
        public Builder expirationDate(final InstantAttributeResponse expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }
        
        public Builder customerType(final StringAttributeResponse customerType) {
            this.customerType = customerType;
            return this;
        }
        
        public Builder language(final StringAttributeResponse language) {
            this.language = language;
            return this;
        }
        
        
        public GetUserResponse build() {
            return new GetUserResponse(id, organization, firstName, lastName, email, address, timezone, approvedAgreements, approvedMarketInfo, licensePlates,
                                              socialSecurityNumber, customerNumber, hasChargingAccess, customerType, phoneNumbers,
                                              organizationNumber, organizationPermissions, roles, language,
                                              activationDate, expirationDate);
        }
    }

}
