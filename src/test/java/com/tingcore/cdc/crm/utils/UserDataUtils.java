package com.tingcore.cdc.crm.utils;

import com.google.common.collect.Lists;
import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.StringAttribute;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.v1.request.AttributeValueListRequest;
import com.tingcore.users.model.v1.response.AttributeResponse;
import com.tingcore.users.model.v1.response.UserResponse;

import java.lang.reflect.AccessibleObject;
import java.time.Instant;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author moa.mackegard
 * @since 2017-11-12.
 */
public class UserDataUtils {


    public UserDataUtils() {
    }

    public static User createGetUserResponse() {
        return User.createBuilder()
                .id(CommonDataUtils.getNextId())
                .email(CommonDataUtils.randomEmail())
                .customerNumber(new StringAttribute(CommonDataUtils.randomLong(1000, 5000), CommonDataUtils.randomNumberStr(1000, 5000)))
                .build();
    }

    public static UserResponse.Builder createValidUserResponse() {
        return UserResponse.createBuilder()
                .organization(OrganizationDataUtils.createOrganizationResponse().build())
                .accessibleOrganizations(newArrayList(OrganizationDataUtils.createOrganizationResponse().build()))
                .email(CommonDataUtils.randomEmail())
                .id(CommonDataUtils.getNextId())
                .created(Instant.now())
                .attributes(newArrayList(
                        AttributeDataUtils.createFirstNameResponse(),
                        AttributeDataUtils.createLastNameResponse(),
                        AttributeDataUtils.createPhoneNumberResponse(),
                        AttributeDataUtils.createPhoneNumberResponse(),
                        AttributeDataUtils.createPhoneNumberResponse(),
                        AttributeDataUtils.createApprovedTermsConditionsResponse()
                ));
    }

    public static UpdatePrivateCustomerRequest createUpdatePrivateCustomerRequest() {
        return UpdatePrivateCustomerRequest.createBuilder()
                .address(Lists.newArrayList(AttributeDataUtils.createAddress(), AttributeDataUtils.createAddress()))
                .approvedTermsConditions(Collections.singletonList(AttributeDataUtils.createApprovedTermsConditions()))
                .approvedMarketInfo(Collections.singletonList(AttributeDataUtils.createApprovedMarketInfo()))
                .approvedPrivacyPolicies(Collections.singletonList(AttributeDataUtils.createApprovedPrivacy()))
                .firstName(AttributeDataUtils.createStringAttribute(AttributeConstant.FIRST_NAME))
                .lastName(AttributeDataUtils.createStringAttribute(AttributeConstant.LAST_NAME))
                .phoneNumbers(Collections.singletonList(AttributeDataUtils.createPhoneNumber()))
                .licensePlates(Collections.singletonList(AttributeDataUtils.createLicensePlate()))
                .language(AttributeDataUtils.createStringAttribute(AttributeConstant.LANGUAGE))
                .socialSecurityNumber(AttributeDataUtils.createSocialSecurityNumber())
                .timezone(AttributeDataUtils.createStringAttribute(AttributeConstant.TIMEZONE))
                .build();
    }

    public static User createUpdatePrivateCustomerResponse(UpdatePrivateCustomerRequest request) {
        return User.createBuilder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumbers(request.getPhoneNumbers())
                .approvedMarketInfo(request.getApprovedMarketInfo())
                .address(request.getAddresses())
                .timezone(request.getTimezone())
                .approvedTermsConditions(request.getApprovedTermsConditions())
                .licensePlates(request.getLicensePlates())
                .socialSecurityNumber(request.getSocialSecurityNumber())
                .language(request.getLanguage())
                .build();
    }

    public static UpdateBusinessCustomerRequest createUpdateBusinessCustomerRequest() {
        return UpdateBusinessCustomerRequest.createBuilder()
                .address(Lists.newArrayList(AttributeDataUtils.createAddress(), AttributeDataUtils.createAddress()))
                .approvedTermsConditions(Collections.singletonList(AttributeDataUtils.createApprovedTermsConditions()))
                .approvedMarketInfo(Collections.singletonList(AttributeDataUtils.createApprovedMarketInfo()))
                .approvedPrivacyPolicies(Collections.singletonList(AttributeDataUtils.createApprovedPrivacy()))
                .phoneNumbers(Collections.singletonList(AttributeDataUtils.createPhoneNumber()))
                .licensePlates(Collections.singletonList(AttributeDataUtils.createLicensePlate()))
                .language(AttributeDataUtils.createStringAttribute(AttributeConstant.LANGUAGE))
                .timezone(AttributeDataUtils.createStringAttribute(AttributeConstant.TIMEZONE))
                .name(AttributeDataUtils.createStringAttribute(AttributeConstant.NAME))
                .organizationNumber(AttributeDataUtils.createOrganizationNumber())
                .contactFirstName(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_FIRST_NAME))
                .contactLastName(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_LAST_NAME))
                .contactEmail(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_EMAIL))
                .contactPhoneNumber(Collections.singletonList(AttributeDataUtils.createPhoneNumber()))
                .build();
    }

    public static User createUpdateBusinessCustomerResponse(UpdateBusinessCustomerRequest request) {
        return User.createBuilder()
                .name(request.getName())
                .phoneNumbers(request.getPhoneNumbers())
                .approvedMarketInfo(request.getApprovedMarketInfo())
                .approvedPrivacyPolicies(request.getApprovedPrivacyPolicies())
                .address(request.getAddresses())
                .timezone(request.getTimezone())
                .approvedTermsConditions(request.getApprovedTermsConditions())
                .licensePlates(request.getLicensePlates())
                .organizationNumber(request.getOrganizationNumber())
                .language(request.getLanguage())
                .contactFirstName(request.getContactFirstName())
                .contactLastName(request.getContactLastName())
                .contactEmail(request.getContactEmail())
                .contactPhoneNumber(request.getContactPhoneNumber())
                .build();
    }

    public static List<AttributeResponse> createMockResponseList(AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        List<AttributeResponse> responses = new ArrayList<>();
        AttributeResponseDataUtils.mockOrganizationNumberResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockSocialSecurityNumberResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        responses.addAll(AttributeResponseDataUtils.mockAddressResponse(listRequest, cachedAttributes));
        responses.addAll(AttributeResponseDataUtils.mockLicensePlatesResponse(listRequest, cachedAttributes));
        responses.addAll(AttributeResponseDataUtils.mockPhoneNumbersResponse(listRequest, cachedAttributes));
        responses.addAll(AttributeResponseDataUtils.mockApprovedTermsConditionsResponse(listRequest, cachedAttributes));
        AttributeResponseDataUtils.mockApprovedMarketInfoResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockEmailResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockApprovedPrivacyPolicyResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockTimeZoneResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        return responses;
    }
}

