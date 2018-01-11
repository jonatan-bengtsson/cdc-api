package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.repository.AttributeRepository;
import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.cdc.crm.request.BaseUpdateCustomerRequest;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author moa.mackegard
 * @since 2017-11-08.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private AttributeRepository attributeRepository;

    @Autowired
    public UserService(final UserRepository userRepository, AttributeRepository attributeRepository) {
        this.userRepository = userRepository;
        this.attributeRepository = attributeRepository;
    }

    public User getUserById(final Long authorizedUserId, final Boolean includeAttributes) {
        final ApiResponse<UserResponse> apiResponse = userRepository.findById(authorizedUserId, includeAttributes);
        return apiResponse.getResponseOptional()
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public User putUserAttributeValues(final Long userId, final Long authorizedUserId, final BaseUpdateCustomerRequest userRequest){
        AttributeValueListRequest attributeValueListRequest = AttributeValueMapper.toAttributeValueListRequest(userRequest, attributeRepository.findAll());
        ApiResponse<List<AttributeResponse>> apiResponse = userRepository.putUserAttributeValues(userId, authorizedUserId, attributeValueListRequest);
        return apiResponse.getResponseOptional()
                .map(attributeResponses -> UserMapper.attributeListToUserResponse(attributeResponses))
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

}
