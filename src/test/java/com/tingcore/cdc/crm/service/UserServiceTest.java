package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
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
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void getUserById() throws Exception {
        UserResponse mockResponse = UserDataUtils.createValidUserResponse();
        ApiResponse<UserResponse> apiMockResponse = new ApiResponse<>(mockResponse);
        given(userRepository.findById(mockResponse.getId(), 1L, true)).willReturn(apiMockResponse);
        GetUserResponse response = userService.getUserById(mockResponse.getId(), 1L, true);
        assertThat(response.getFirstName()).isEqualTo(mockResponse.getFirstName());
    }

    @Test
    public void failGetUserByIdNotFound() throws Exception {
        final Long nextId = CommonDataUtils.getNextId();
        final Long authorizedUserId = CommonDataUtils.getNextId();
        ErrorResponse errorResponse = ErrorResponse.notFound().message("Not found").build();
        ApiResponse<UserResponse> apiMockResponse = new ApiResponse<>(errorResponse);
        given(userRepository.findById(nextId, authorizedUserId, true)).willReturn(apiMockResponse);
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> userService.getUserById(nextId, authorizedUserId, true));
    }
}