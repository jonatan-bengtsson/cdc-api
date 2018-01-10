package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;
import com.tingcore.users.model.UserResponse;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
@SuppressWarnings(SuppressWarningConstant.CONSTANT_CONDITIONS)
public class UserMapperTest {

    @Test
    public void mapToGetUserResponse() {
        UserResponse userResponse = UserDataUtils.createValidUserResponse();
        User response = UserMapper.toResponse(userResponse);
        AttributeResponse phoneNumber = userResponse.getAttributes().stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.PHONE_NUMBER))
                .findFirst().get();
        assertThat(response.getPhoneNumbers().get(0).getId()).isEqualTo(phoneNumber.getAttributeValue().getId());
        assertThat(response.getPhoneNumbers().get(0).getPhoneNumber()).isSubstringOf(phoneNumber.getAttributeValue().getValue());

        AttributeResponse firstName = userResponse.getAttributes().stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.FIRST_NAME))
                .findFirst().get();
        assertThat(response.getFirstName().getId()).isEqualTo(firstName.getAttributeValue().getId());
        assertThat(response.getFirstName().getValue()).isEqualTo(firstName.getAttributeValue().getValue());

        AttributeResponse lastName = userResponse.getAttributes().stream()
                .filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.LAST_NAME))
                .findFirst().get();
        assertThat(response.getLastName().getId()).isEqualTo(lastName.getAttributeValue().getId());
        assertThat(response.getLastName().getValue()).isEqualTo(lastName.getAttributeValue().getValue());
    }

    @Test
    public void failMapToGetUserResponseDueToFailedJson() {
        UserResponse userResponse = UserDataUtils.createValidUserResponse();
        final AttributeResponse invalidAttributeResponse = new AttributeResponse();
        invalidAttributeResponse.setType(AttributeResponse.TypeEnum.JSON);
        invalidAttributeResponse.setProperties(new HashMap<>());
        invalidAttributeResponse.setName(AttributeConstant.PHONE_NUMBER);
        invalidAttributeResponse.setId(CommonDataUtils.getNextId());
        final AttributeValueResponse failedJsonAttributeValue = new AttributeValueResponse();
        failedJsonAttributeValue.setId(CommonDataUtils.getNextId());
        failedJsonAttributeValue.setValue(CommonDataUtils.randomUUID());

        invalidAttributeResponse.setAttributeValue(failedJsonAttributeValue);
        userResponse.setAttributes(Collections.singletonList(invalidAttributeResponse));

        User response = UserMapper.toResponse(userResponse);
        assertThat(response.getPhoneNumbers()).hasSize(0);
    }

}
