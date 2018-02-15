package com.tingcore.cdc.crm.utils;

import com.google.common.collect.Lists;
import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.StringAttribute;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.UserResponse;

import java.lang.reflect.AccessibleObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public static UserResponse createValidUserResponse() {
        final UserResponse response = new UserResponse();
        response.setOrganization(OrganizationDataUtils.createOrganizationResponse());
        response.setAccessibleOrganizations(newArrayList(OrganizationDataUtils.createOrganizationResponse()));
        response.setEmail(CommonDataUtils.randomEmail());
        response.setId(CommonDataUtils.getNextId());
        response.setCreated(Instant.now().toEpochMilli());
        response.setAttributes(newArrayList(
                AttributeDataUtils.createFirstNameResponse(),
                AttributeDataUtils.createLastNameResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createApprovedTermsConditionsResponse()
        ));
        return response;
    }

    public static UpdatePrivateCustomerRequest createUpdatePrivateCustomerRequest() {
        return UpdatePrivateCustomerRequest.createBuilder()
                .address(Lists.newArrayList(AttributeDataUtils.createAddress(), AttributeDataUtils.createAddress()))
                .approvedTermsConditions(Arrays.asList(AttributeDataUtils.createApprovedTermsConditions()))
                .approvedMarketInfo(Arrays.asList(AttributeDataUtils.createApprovedMarketInfo()))
                .approvedPrivacyPolicies(Arrays.asList(AttributeDataUtils.createApprovedPrivacy()))
                .firstName(AttributeDataUtils.createStringAttribute(AttributeConstant.FIRST_NAME))
                .lastName(AttributeDataUtils.createStringAttribute(AttributeConstant.LAST_NAME))
                .phoneNumbers(Arrays.asList(AttributeDataUtils.createPhoneNumber()))
                .licensePlates(Arrays.asList(AttributeDataUtils.createLicensePlate()))
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
                .approvedTermsConditions(Arrays.asList(AttributeDataUtils.createApprovedTermsConditions()))
                .approvedMarketInfo(Arrays.asList(AttributeDataUtils.createApprovedMarketInfo()))
                .approvedPrivacyPolicies(Arrays.asList(AttributeDataUtils.createApprovedPrivacy()))
                .phoneNumbers(Arrays.asList(AttributeDataUtils.createPhoneNumber()))
                .licensePlates(Arrays.asList(AttributeDataUtils.createLicensePlate()))
                .language(AttributeDataUtils.createStringAttribute(AttributeConstant.LANGUAGE))
                .timezone(AttributeDataUtils.createStringAttribute(AttributeConstant.TIMEZONE))
                .name(AttributeDataUtils.createStringAttribute(AttributeConstant.NAME))
                .organizationNumber(AttributeDataUtils.createOrganizationNumber())
                .contactFirstName(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_FIRST_NAME))
                .contactLastName(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_LAST_NAME))
                .contactEmail(AttributeDataUtils.createStringAttribute(AttributeConstant.CONTACT_EMAIL))
                .contactPhoneNumber(Arrays.asList(AttributeDataUtils.createPhoneNumber()))
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

    public static List<AttributeResponse> createMockResponseList (AttributeValueListRequest listRequest, Map<String, Long> cachedAttributes) {
        List<AttributeResponse> responses = new ArrayList<>();
        AttributeResponseDataUtils.mockOrganizationNumberResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockSocialSecurityNumberResponse(listRequest, cachedAttributes).ifPresent(responses::add);
        AttributeResponseDataUtils.mockAddressResponse(listRequest, cachedAttributes).forEach(responses::add);
        responses.addAll(AttributeResponseDataUtils.mockLicensePlatesResponse(listRequest, cachedAttributes));
        responses.addAll(AttributeResponseDataUtils.mockPhoneNumbersResponse(listRequest, cachedAttributes));
        AttributeResponseDataUtils.mockApprovedTermsConditionsResponse(listRequest, cachedAttributes).forEach(approvedTermsConditions -> responses.add(approvedTermsConditions));
        AttributeResponseDataUtils.mockApprovedMarketInfoResponse(listRequest, cachedAttributes).ifPresent(approvedMarketInfo -> responses.add(approvedMarketInfo));
        AttributeResponseDataUtils.mockEmailResponse(listRequest, cachedAttributes).ifPresent(email -> responses.add(email));
        AttributeResponseDataUtils.mockApprovedPrivacyPolicyResponse(listRequest, cachedAttributes).ifPresent(approvedPrivacy -> responses.add(approvedPrivacy));
        AttributeResponseDataUtils.mockTimeZoneResponse(listRequest, cachedAttributes).ifPresent(timezone -> responses.add(timezone));
        return responses;
    }
}

