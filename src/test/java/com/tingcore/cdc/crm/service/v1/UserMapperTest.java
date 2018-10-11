package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.constant.AttributeType;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.core.constant.SuppressWarningConstant;
import com.tingcore.users.model.v1.response.AttributeResponse;
import com.tingcore.users.model.v1.response.AttributeValueResponse;
import com.tingcore.users.model.v1.response.UserResponse;
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
        UserResponse userResponse = UserDataUtils.createValidUserResponse().build();
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
        final AttributeResponse.Builder invalidAttributeResponse = AttributeResponse.createBuilder()
                .type(AttributeType.JSON)
                .properties(new HashMap<>())
                .name(AttributeConstant.PHONE_NUMBER)
                .id(CommonDataUtils.getNextId())
                ;
        final AttributeValueResponse failedJsonAttributeValue = AttributeValueResponse.createBuilder()
                .id(CommonDataUtils.getNextId())
                .value(CommonDataUtils.randomUUID())
                .build();

        invalidAttributeResponse.attributeValue(failedJsonAttributeValue);
        UserResponse userResponse = UserDataUtils.createValidUserResponse().attributes(Collections.singletonList(invalidAttributeResponse.build())).build();

        User response = UserMapper.toResponse(userResponse);
        assertThat(response.getPhoneNumbers()).hasSize(0);
    }

}
