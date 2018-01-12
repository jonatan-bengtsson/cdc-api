package com.tingcore.cdc.crm.utils;

import com.google.common.collect.Lists;
import com.tingcore.cdc.crm.model.StringAttribute;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.UserResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        response.setOrganizationPermissions(newArrayList(OrganizationDataUtils.createOrganizationResponse()));
        response.setEmail(CommonDataUtils.randomEmail());
        response.setId(CommonDataUtils.getNextId());
        response.setCreated(Instant.now().toEpochMilli());
        response.setAttributes(newArrayList(
                AttributeDataUtils.createFirstNameResponse(),
                AttributeDataUtils.createLastNameResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createPhoneNumberResponse(),
                AttributeDataUtils.createApprovedAgreementResponse()
        ));
        return response;
    }

    public static UpdatePrivateCustomerRequest createUpdatePrivateCustomerRequest() {
        return UpdatePrivateCustomerRequest.createBuilder()
                .address(Lists.newArrayList(ModelDataUtils.createAddress(), ModelDataUtils.createAddress()))
                .approvedAgreements(Arrays.asList(ModelDataUtils.createApprovedAgreement()))
                .approvedMarketInfo(ModelDataUtils.createApprovedMarketInfo())
                .approvedPrivacy(ModelDataUtils.createApprovedPrivacy())
                .firstName(ModelDataUtils.createStringAttribute())
                .lastName(ModelDataUtils.createStringAttribute())
                .phoneNumbers(Arrays.asList(ModelDataUtils.createPhoneNumber()))
                .licensePlates(Arrays.asList(ModelDataUtils.createLicensePlate()))
                .language(ModelDataUtils.createStringAttribute())
                .socialSecurityNumber(ModelDataUtils.createSocialSecurityNumber())
                .timezone(ModelDataUtils.createStringAttribute())
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
                .approvedAgreements(request.getApprovedAgreements())
                .licensePlates(request.getLicensePlates())
                .socialSecurityNumber(request.getSocialSecurityNumber())
                .language(request.getLanguage())
                .build();
    }

    public static UpdateBusinessCustomerRequest createUpdateBusinessCustomerRequest() {
        return UpdateBusinessCustomerRequest.createBuilder()
                .address(Lists.newArrayList(ModelDataUtils.createAddress(), ModelDataUtils.createAddress()))
                .approvedAgreements(Arrays.asList(ModelDataUtils.createApprovedAgreement()))
                .approvedMarketInfo(ModelDataUtils.createApprovedMarketInfo())
                .approvedPrivacy(ModelDataUtils.createApprovedPrivacy())
                .phoneNumbers(Arrays.asList(ModelDataUtils.createPhoneNumber()))
                .licensePlates(Arrays.asList(ModelDataUtils.createLicensePlate()))
                .language(ModelDataUtils.createStringAttribute())
                .timezone(ModelDataUtils.createStringAttribute())
                .name(ModelDataUtils.createStringAttribute())
                .organizationNumber(ModelDataUtils.createOrganizationNumber())
                .contactFirstName(ModelDataUtils.createStringAttribute())
                .contactLastName(ModelDataUtils.createStringAttribute())
                .contactEmail(ModelDataUtils.createStringAttribute())
                .contactPhoneNumber(Arrays.asList(ModelDataUtils.createPhoneNumber()))
                .build();
    }

    public static User createUpdateBusinessCustomerResponse(UpdateBusinessCustomerRequest request) {
        return User.createBuilder()
                .name(request.getName())
                .phoneNumbers(request.getPhoneNumbers())
                .approvedMarketInfo(request.getApprovedMarketInfo())
                .approvedPrivacy(request.getApprovedPrivacy())
                .address(request.getAddresses())
                .timezone(request.getTimezone())
                .approvedAgreements(request.getApprovedAgreements())
                .licensePlates(request.getLicensePlates())
                .organizationNumber(request.getOrganizationNumber())
                .language(request.getLanguage())
                .contactFirstName(request.getContactFirstName())
                .contactLastName(request.getContactLastName())
                .contactEmail(request.getContactEmail())
                .contactPhoneNumber(request.getContactPhoneNumber())
                .build();
    }

    public static List<AttributeResponse> createMockResponseList (AttributeValueListRequest listRequest) {
        List<AttributeResponse> responses = new ArrayList<>();
        AttributeResponseDataUtils.mockOrganizationResponse(listRequest).ifPresent(responses::add);
        AttributeResponseDataUtils.mockSocialSecurityNumberResponse(listRequest).ifPresent(responses::add);
        AttributeResponseDataUtils.mockAddressResponse(listRequest).forEach(responses::add);
        responses.addAll(AttributeResponseDataUtils.mockLicensePlatesResponse(listRequest));
        responses.addAll(AttributeResponseDataUtils.mockPhoneNumbersResponse(listRequest));
        AttributeResponseDataUtils.mockApprovedAgreementResponse(listRequest).forEach(approvedAgreement -> responses.add(approvedAgreement));
        AttributeResponseDataUtils.mockApprovedMarketInfoResponse(listRequest).ifPresent(approvedMarketInfo -> responses.add(approvedMarketInfo));
        AttributeResponseDataUtils.mockEmailResponse(listRequest).ifPresent(email -> responses.add(email));
        AttributeResponseDataUtils.mockApprovedPrivacyResponse(listRequest).ifPresent(approvedPrivacy -> responses.add(approvedPrivacy));
        AttributeResponseDataUtils.mockTimeZoneResponse(listRequest).ifPresent(timezone -> responses.add(timezone));
        return responses;
    }
}

