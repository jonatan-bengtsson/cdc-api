package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.model.StringAttribute;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.UserResponse;

import java.time.Instant;

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

}

