package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.cdc.crm.response.StringAttributeResponse;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;
import com.tingcore.users.model.OrganizationResponse;
import com.tingcore.users.model.UserResponse;

import java.util.Collections;
import java.util.UUID;

/**
 * @author moa.mackegard
 * @since 2017-11-12.
 */
public class UserDataUtils {

    public UserDataUtils() {
    }

    public static GetUserResponse createGetUserResponse() {
        return GetUserResponse.createBuilder()
                .id(11L)
                .firstName("firstname")
                .lastName("lastname")
                .email("email")
                .customerNumber(new StringAttributeResponse(2222L, "12345"))
                .build();
    }

    public static UserResponse createValidUserResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(CommonDataUtils.getNextId());
        userResponse.setFirstName("firstname");
        userResponse.setLastName("lastname");
        userResponse.setEmail("email");
        String telephone = "{\"phoneNumber\":\"+467634512312\", \"formatter\":\"Swedish\"}";

        final OrganizationResponse organizationResponse = new OrganizationResponse();
        organizationResponse.setId(CommonDataUtils.getNextId());
        organizationResponse.setName(UUID.randomUUID().toString());
        organizationResponse.setCreated(CommonDataUtils.getRandomPastTimestamp());

        userResponse.setOrganization(organizationResponse);

        AttributeResponse telephoneAttribute = new AttributeResponse();
        AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
        attributeValueResponse.setValue(telephone);
        attributeValueResponse.setId(1L);

        telephoneAttribute.setAttributeValue(attributeValueResponse);
        telephoneAttribute.setId(11L);
        telephoneAttribute.setType(AttributeResponse.TypeEnum.JSON);
        telephoneAttribute.setName("phoneNumber");

        userResponse.setAttributes(Collections.singletonList(telephoneAttribute));
        return userResponse;
    }

}
