package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.cdc.crm.response.GetUserResponse;
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

    public GetUserResponse getUserById(final Long virtualId, final Long authorizedUserId, final Boolean includeAttributes) {
        final ApiResponse<UserResponse> apiResponse = userRepository.findById(virtualId, authorizedUserId, includeAttributes);
        return apiResponse.getResponseOptional()
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

}
