package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.constant.SuppressWarningConstant;
import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.users.model.UserResponse;
import org.junit.Test;

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
        GetUserResponse response = UserMapper.toResponse(userResponse);
        Long phoneNumberId = userResponse.getAttributes().stream().filter(attributeResponse -> attributeResponse.getName().equals("phoneNumber")).map(attributeResponse -> attributeResponse.getAttributeValue().getId()).findFirst().get();
        assertThat(response.getPhoneNumbers().get(0).getId()).isEqualTo(phoneNumberId);
    }

}
