package com.tingcore.cdc.crm.service;


import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.response.*;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.OrganizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author markusanderssonnoren
 * @since 2017-11-20.
 */
public class AttributeMapper {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserMapper.class);
    
    public static Optional<StringAttributeResponse> findStringAttribute(final List<AttributeResponse> attributes, final String attributeName) {
        return findStringAttributes(attributes, attributeName).stream().findAny();
    }
    
    public static List<StringAttributeResponse> findStringAttributes(final List<AttributeResponse> attributes, final String attributeName){
        return findAttributesFromList(attributes, attributeName).stream()
                       .map(attributeResponse -> new StringAttributeResponse(attributeResponse.getAttributeValue().getId(), attributeResponse.getAttributeValue().getValue()))
                       .collect(Collectors.toList());
    }
    
    private static List<AttributeResponse> findAttributesFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).collect(Collectors.toList());
    }
    
    private static Optional<AttributeResponse> findAttributeFromList(final List<AttributeResponse> attributes, final String attributeName) {
        return attributes.stream().filter(attribute -> attribute.getName().equals(attributeName)).findAny();
    }
    
    private static <T extends BaseAttributeResponse> List<T> addAttributeIdToAttributeResponses(List<AttributeResponse> attributeResponses, List<T> attributes) {
        IntStream.range(0, attributeResponses.size())
                .forEach(index -> attributes.get(index).setId(attributeResponses.get(index).getAttributeValue().getId()));
        return attributes;
    }
    
    private static <T> Optional<T> parseAttributeValue(final AttributeResponse attributeResponse, final Class<T> clazz) {
        return JsonUtils.fromJson(attributeResponse.getAttributeValue().getValue(), clazz)
                       .map(Optional::of)
                       .orElseGet(() -> {
                           LOG.warn("Unable to parse attribute value JSON for attribute {} : {}", attributeResponse.getName(), attributeResponse.getAttributeValue().getValue());
                           return Optional.empty();
                       });
    }
    
    
    
    static List<ApprovedAgreementResponse> findApprovedTermsResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.APPROVED_AGREEMENTS);
        List<ApprovedAgreementResponse> approvedTerms = attributeResponses.stream()
                                                                .map(attributeResponse -> parseAttributeValue(attributeResponse, ApprovedAgreementResponse.class))
                                                                .filter(Optional::isPresent)
                                                                .map(Optional::get)
                                                                .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, approvedTerms);
    }
    
    static List<PhoneNumberResponse> findPhoneNumberResponses(final List<AttributeResponse> attributes, String phoneNumberType) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, phoneNumberType);
        List<PhoneNumberResponse> phoneNumbers = attributeResponses.stream()
                                                         .map(attributeResponse -> parseAttributeValue(attributeResponse, PhoneNumberResponse.class))
                                                         .filter(Optional::isPresent)
                                                         .map(Optional::get)
                                                         .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, phoneNumbers);
    }
    
    static Optional<AddressResponse> findBillingAddressResponse(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ADDRESS);
        
        if(attributeResponse.isPresent()){
            Optional<AddressResponse> addressResponse = parseAttributeValue(attributeResponse.get(), AddressResponse.class);
            if(addressResponse.isPresent()){
                addressResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return addressResponse;
        } else {
            return Optional.empty();
        }
    }
    
    static Optional<ApprovedMarketInfoResponse> findApprovedMarketInfo(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.APPROVED_MARKET_INFO);
        
        if(attributeResponse.isPresent()){
            Optional<ApprovedMarketInfoResponse> approvedMarketInfoResponse = parseAttributeValue(attributeResponse.get(), ApprovedMarketInfoResponse.class);
            if(approvedMarketInfoResponse.isPresent()){
                approvedMarketInfoResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return approvedMarketInfoResponse;
        } else {
            return Optional.empty();
        }
    }
    
    
    static List<AddressResponse> findAddressResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.ADDRESS);
        List<AddressResponse> addresses = attributeResponses.stream()
                                                  .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                                                  .filter(Optional::isPresent)
                                                  .map(Optional::get)
                                                  .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, addresses);
    }
    
    static List<LicensePlateResponse> findLicensePlateResponse(final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.LICENSE_PLATES);
        List<LicensePlateResponse> licensePlates = attributeResponses.stream()
                                                           .map(attributeResponse -> parseAttributeValue(attributeResponse, LicensePlateResponse.class))
                                                           .filter(Optional::isPresent)
                                                           .map(Optional::get)
                                                           .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, licensePlates);
    }
    
    static Optional<SocialSecurityNumberResponse> findSocialSecurityNumber(final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.SOCIAL_SECURITY_NUMBER);
        
        if(attributeResponse.isPresent()){
            Optional<SocialSecurityNumberResponse> socialSecurityNumberResponse = parseAttributeValue(attributeResponse.get(), SocialSecurityNumberResponse.class);
            if(socialSecurityNumberResponse.isPresent()){
                socialSecurityNumberResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return socialSecurityNumberResponse;
        } else {
            return Optional.empty();
        }
    }
    
    public static Optional<OrganizationNumberResponse> findOrganizationNumber (final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.ORGANIZATION_NUMBER);
        
        if(attributeResponse.isPresent()){
            Optional<OrganizationNumberResponse> organizationNumberResponse = parseAttributeValue(attributeResponse.get(), OrganizationNumberResponse.class);
            if(organizationNumberResponse.isPresent()){
                organizationNumberResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return organizationNumberResponse;
        } else {
            return Optional.empty();
        }
    }
    
    public static Optional<VatResponse> findVat (final List<AttributeResponse> attributes) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, AttributeConstant.VAT);
        
        if(attributeResponse.isPresent()){
            Optional<VatResponse> vatResponse = parseAttributeValue(attributeResponse.get(), VatResponse.class);
            if(vatResponse.isPresent()){
                vatResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return vatResponse;
        } else {
            return Optional.empty();
        }
    }
    
    public static List<AddressResponse> findVisitingAddressResponse (final List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.VISITING_ADDRESS);
        List<AddressResponse> addresses = attributeResponses.stream()
                                                  .map(attributeResponse -> parseAttributeValue(attributeResponse, AddressResponse.class))
                                                  .filter(Optional::isPresent)
                                                  .map(Optional::get)
                                                  .collect(Collectors.toList());
        return addAttributeIdToAttributeResponses(attributeResponses, addresses);
    }
    
    public static List<GetOrganizationResponse> findOrganizationPermissions(List<AttributeResponse> attributes) {
        List<AttributeResponse> attributeResponses = findAttributesFromList(attributes, AttributeConstant.ORGANIZATION_PERMISSIONS);
        List<GetOrganizationResponse> organizationPermissions = attributeResponses.stream()
                                                                        .map(attributeResponse -> parseAttributeValue(attributeResponse, GetOrganizationResponse.class))
                                                                        .filter(Optional::isPresent)
                                                                        .map(Optional::get)
                                                                        .collect(Collectors.toList());
        IntStream.range(0, attributeResponses.size())
                .forEach(index -> organizationPermissions.get(index).setId(attributeResponses.get(index).getAttributeValue().getId()));
        return organizationPermissions;
    }
    
    public static Optional<BooleanAttributeResponse> findBooleanAttribute(List<AttributeResponse> attributes, final String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);
        
        if(attributeResponse.isPresent()){
            Optional<BooleanAttributeResponse> booleanAttributeResponse = parseAttributeValue(attributeResponse.get(), BooleanAttributeResponse.class);
            if(booleanAttributeResponse.isPresent()){
                booleanAttributeResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return booleanAttributeResponse;
        } else {
            return Optional.empty();
        }
    }
    
    public static Optional<InstantAttributeResponse> findDateResponse(List<AttributeResponse> attributes, String attributeName) {
        Optional<AttributeResponse> attributeResponse = findAttributeFromList(attributes, attributeName);
        
        if(attributeResponse.isPresent()){
            Optional<InstantAttributeResponse> instantAttributeResponse = parseAttributeValue(attributeResponse.get(), InstantAttributeResponse.class);
            if(instantAttributeResponse.isPresent()){
                instantAttributeResponse.get().setId(attributeResponse.get().getAttributeValue().getId());
            }
            return instantAttributeResponse;
        } else {
            return Optional.empty();
        }
    }
    
    public static Optional<GetOrganizationResponse> mapOrganization(final OrganizationResponse organization ) {
        // mappa om OrganizationResponse till vad vi vill ha
        return Optional.empty();
    }
}
