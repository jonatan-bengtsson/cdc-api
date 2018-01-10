package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.UserResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;


/**
 * @author moa.mackegard
 * @since 2017-11-12.
 */
@SuppressWarnings(SuppressWarningConstant.CONSTANT_CONDITIONS)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository, attributeRepository);
    }

    @Test
    public void getUserById() {
        UserResponse mockResponse = UserDataUtils.createValidUserResponse();
        ApiResponse<UserResponse> apiMockResponse = new ApiResponse<>(mockResponse);
        final Long authorizationId = CommonDataUtils.getNextId();
        given(userRepository.findById(authorizationId, true)).willReturn(apiMockResponse);
        User response = userService.getUserById(authorizationId, true);
        assertThat(response.getFirstName().getValue()).isEqualTo(mockResponse.getAttributes().stream().filter(attributeResponse -> attributeResponse.getName().equals(AttributeConstant.FIRST_NAME)).findFirst().get().getAttributeValue().getValue());
    }

    @Test
    public void failGetUserByIdNotFound() {
        final Long nextId = CommonDataUtils.getNextId();
        final Long authorizedUserId = CommonDataUtils.getNextId();
        ErrorResponse errorResponse = ErrorResponse.notFound().message("Not found").build();
        ApiResponse<UserResponse> apiMockResponse = new ApiResponse<>(errorResponse);
        given(userRepository.findById(authorizedUserId, true)).willReturn(apiMockResponse);
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> userService.getUserById(authorizedUserId, true));
    }
}
