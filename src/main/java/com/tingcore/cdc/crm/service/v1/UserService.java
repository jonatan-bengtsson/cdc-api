package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.repository.v1.AttributeRepository;
import com.tingcore.cdc.crm.repository.v1.UserRepository;
import com.tingcore.cdc.crm.request.BaseUpdateCustomerRequest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.v1.request.AttributeValueListRequest;
import com.tingcore.users.model.v1.response.AttributeResponse;
import com.tingcore.users.model.v1.response.UserResponse;
import com.tingcore.users.model.v2.response.Organization;
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

    public <T extends BaseUpdateCustomerRequest> User putUserAttributeValues(final Long userId, final T userRequest){
        AttributeValueListRequest attributeValueListRequest = AttributeValueMapper.toAttributeValueListRequest(userRequest, attributeRepository.findAll());
        ApiResponse<List<AttributeResponse>> apiResponse = userRepository.putUserAttributeValues(userId, attributeValueListRequest);
        return apiResponse.getResponseOptional()
                .map(UserMapper::attributeListToUserResponse)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public Organization getOrganisationByUserPrefix(String userPrefix) {
        Organization organization = new Organization();
        organization.setId(2L); // TODO, awaiting endpoint in user service.
        return organization;
    }
}
