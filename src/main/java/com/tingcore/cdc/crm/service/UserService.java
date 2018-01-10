package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moa.mackegard
 * @since 2017-11-08.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(final Long authorizedUserId, final Boolean includeAttributes) {
        final ApiResponse<UserResponse> apiResponse = userRepository.findById(authorizedUserId, includeAttributes);
        return apiResponse.getResponseOptional()
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

}
