package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.repository.v1.AttributeRepository;
import com.tingcore.cdc.crm.repository.v1.UserRepository;
import com.tingcore.cdc.crm.request.BaseUpdateCustomerRequest;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.AttributeDataUtils;
import com.tingcore.cdc.crm.utils.UserDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v1.request.AttributeValueListRequest;
import com.tingcore.users.model.v1.response.AttributeResponse;
import com.tingcore.users.model.v1.response.UserResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;


/**
 * @author moa.mackegard
 * @since 2017-11-12.
 */
@SuppressWarnings(SuppressWarningConstant.CONSTANT_CONDITIONS)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean private UserRepository userRepository;
    @MockBean private AttributeRepository attributeRepository;

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository, attributeRepository);
    }

    @Test
    public void getUserById() {
        UserResponse mockResponse = UserDataUtils.createValidUserResponse().build();
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

    @Test
    public void putPrivateCustomerAttributeValues() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final UpdatePrivateCustomerRequest request = UserDataUtils.createUpdatePrivateCustomerRequest();

        List<AttributeResponse> mockCachedAttributes = AttributeDataUtils.allAttributes();
        Map<String, Long> attributeIdMap = AttributeValueMapper.createAttributeIdMap(mockCachedAttributes);
        given(attributeRepository.findAll()).willReturn(mockCachedAttributes);

        AttributeValueListRequest attributeValueListRequest = AttributeValueMapper.toAttributeValueListRequest(request, mockCachedAttributes);
        ApiResponse<List<AttributeResponse>> apiMockResponse = createApiMockResponse(attributeValueListRequest, attributeIdMap);
        given(userRepository.putUserAttributeValues(anyLong(), any(AttributeValueListRequest.class))).willReturn(apiMockResponse);

        User userServiceResponse = userService.putUserAttributeValues(userId, request);
        assertCustomer(request, userServiceResponse);
    }

    @Test
    public void failPutPrivateCustomerAttributeValuesApiError () throws Exception {
        given(userRepository.putUserAttributeValues(anyLong(), any(AttributeValueListRequest.class))).willReturn(new ApiResponse<>(ErrorResponse.forbidden().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> userService.putUserAttributeValues(CommonDataUtils.getNextId(), UserDataUtils.createUpdatePrivateCustomerRequest()))
                .withNoCause();
    }

    @Test
    public void putBusinessCustomerAttributeValues() throws Exception {
        final Long userId = CommonDataUtils.getNextId();
        final UpdateBusinessCustomerRequest request = UserDataUtils.createUpdateBusinessCustomerRequest();

        List<AttributeResponse> mockCachedAttributes = AttributeDataUtils.allAttributes();
        Map<String, Long> attributeIdMap = AttributeValueMapper.createAttributeIdMap(mockCachedAttributes);
        given(attributeRepository.findAll()).willReturn(mockCachedAttributes);

        AttributeValueListRequest attributeValueListRequest = AttributeValueMapper.toAttributeValueListRequest(request, mockCachedAttributes);
        ApiResponse<List<AttributeResponse>> apiMockResponse = createApiMockResponse(attributeValueListRequest, attributeIdMap);
        given(userRepository.putUserAttributeValues(anyLong(), any(AttributeValueListRequest.class))).willReturn(apiMockResponse);

        User userServiceResponse = userService.putUserAttributeValues(userId, request);
        assertCustomer(request, userServiceResponse);
    }


    private ApiResponse<List<AttributeResponse>> createApiMockResponse (AttributeValueListRequest listRequest, Map<String, Long> attributeIdMap) {
        List<AttributeResponse> mockResponseList = UserDataUtils.createMockResponseList(listRequest, attributeIdMap);
        ApiResponse<List<AttributeResponse>> apiMockResponse = new ApiResponse<>(mockResponseList);
        return apiMockResponse;
    }

    private void assertCustomer (BaseUpdateCustomerRequest mockUserRequest, User response) {
        assertThat(response.getAddress().get(0).getAddress()).isEqualTo(mockUserRequest.getAddresses().get(0).getAddress());
        assertThat(response.getPhoneNumbers().get(0).getPhoneNumber()).isEqualTo(mockUserRequest.getPhoneNumbers().get(0).getPhoneNumber());
        assertThat(response.getTimezone().getValue()).contains(mockUserRequest.getTimezone().getValue());
        assertThat(response.getLicensePlates().get(0).getLicensePlate()).isEqualTo(mockUserRequest.getLicensePlates().get(0).getLicensePlate());
        assertThat(response.getApprovedTermsConditions().get(0).getUrl()).isEqualTo(mockUserRequest.getApprovedTermsConditions().get(0).getUrl());

    }

}
