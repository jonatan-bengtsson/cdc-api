package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.repository.CustomerKeyRepository;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.api.utils.PaginationConverterService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.*;
import org.hashids.Hashids;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;

/**
 * @author palmithor
 * @since 2017-12-19
 */
@RunWith(SpringRunner.class)
public class CustomerKeyServiceTest {

    @MockBean private CustomerKeyRepository customerKeyRepository;


    private CustomerKeyService customerKeyService;

    @Before
    public void setUp() {
        final PaginationConverterService paginationConverterService = new PaginationConverterService(new HashIdService(new Hashids(CommonDataUtils.randomUUID())));
        customerKeyService = new CustomerKeyService(customerKeyRepository, paginationConverterService);
    }

    @Test
    public void findByUserId() {
        final Long userId = CommonDataUtils.getNextId();
        final PageResponse<CustomerKeyResponse, Long> mockResponse = CustomerKeyDataUtils.randomPageResponse();
        given(customerKeyRepository.findByUserId(userId)).willReturn(new ApiResponse<>(mockResponse));
        final PageResponse<CustomerKey, String> response = customerKeyService.findByUserId(userId);
        final List<CustomerKey> content = response.getContent();
        assertThat(content).hasSize(mockResponse.getContent().size());
        content.forEach(customerKey -> {
            assertThat(customerKey).hasNoNullFieldsOrProperties();
            assertThat(customerKey.getUserPaymentOptions()).hasSize(1);
            assertThat(customerKey.getUserPaymentOptions().get(0)).hasNoNullFieldsOrProperties();
        });
    }

    @Test
    public void failFindByUserId() {
        final Long userId = CommonDataUtils.getNextId();
        given(customerKeyRepository.findByUserId(userId)).willReturn(new ApiResponse<>(ErrorResponse.forbidden().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> customerKeyService.findByUserId(userId))
                .withNoCause();
    }

    @Test
    public void findByCustomerKeyId() {
        final Long userId = CommonDataUtils.getNextId();
        final Long customerKeyId = CommonDataUtils.getNextId();
        final CustomerKeyResponse mockResponse = CustomerKeyDataUtils.randomCustomerKeyResponse();
        given(customerKeyRepository.findById(userId, customerKeyId)).willReturn(new ApiResponse<>(mockResponse));
        final CustomerKey customerKey = customerKeyService.findByCustomerKeyId(userId, customerKeyId);
        assertThat(customerKey).hasNoNullFieldsOrProperties();
        assertMapping(mockResponse, customerKey);
    }

    @Test
    public void failFindByCustomerKeyIdApiError() {
        final Long userId = CommonDataUtils.getNextId();
        final Long customerKeyId = CommonDataUtils.getNextId();
        given(customerKeyRepository.findById(userId, customerKeyId)).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> customerKeyService.findByCustomerKeyId(userId, customerKeyId))
                .withNoCause();
    }

    @Test
    public void create() {
        final CustomerKeyResponse expectedResponse = CustomerKeyDataUtils.randomCustomerKeyResponse();
        given(customerKeyRepository.post(anyLong(), any(CustomerKeyRequest.class)))
                .willReturn(new ApiResponse<>(expectedResponse));
        final CustomerKey customerKey = customerKeyService.create(CommonDataUtils.getNextId(), CustomerKeyDataUtils.randomRequestAllValid().build());
        assertMapping(expectedResponse, customerKey);
    }

    @Test
    public void failCreateApiError() {
        given(customerKeyRepository.post(anyLong(), any(CustomerKeyRequest.class)))
                .willReturn(new ApiResponse<>(ErrorResponse.forbidden().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> customerKeyService.create(CommonDataUtils.getNextId(), CustomerKeyDataUtils.randomRequestAllValid().build()))
                .withNoCause();
    }

    @Test
    public void getCustomerKeyTypes() {
        final List<CustomerKeyTypeResponse> apiResponse = newArrayList(CustomerKeyDataUtils.randomCustomerKeyTypeResponse(), CustomerKeyDataUtils.randomCustomerKeyTypeResponse());
        given(customerKeyRepository.findCustomerKeyTypes())
                .willReturn(new ApiResponse<>(apiResponse));
        final List<CustomerKeyType> customerKeyTypes = customerKeyService.getCustomerKeyTypes();
        assertThat(customerKeyTypes).hasSameSizeAs(apiResponse);
    }

    @Test
    public void failGetCustomerKeyTypesApiError() {
        given(customerKeyRepository.findCustomerKeyTypes())
                .willReturn(new ApiResponse<>(ErrorResponse.serverError().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> customerKeyService.getCustomerKeyTypes())
                .withNoCause();
    }

    @Test
    public void addUserPaymentOption() {
        final CustomerKeyResponse expectedResponse = CustomerKeyDataUtils.randomCustomerKeyResponse();
        given(customerKeyRepository.addUserPaymentOption(anyLong(), any(UserPaymentOptionIdRequest.class), anyLong(), anyLong()))
                .willReturn(new ApiResponse<>(expectedResponse));
        final CustomerKey customerKey = customerKeyService.addUserPaymentOption(
                expectedResponse.getUserId(),
                expectedResponse.getId(),
                expectedResponse.getUserPaymentOptions().get(0).getId());
        assertMapping(expectedResponse, customerKey);
    }

    @Test
    public void failAddUserPaymentOptionApiError() {
        given(customerKeyRepository.addUserPaymentOption(anyLong(), any(UserPaymentOptionIdRequest.class), anyLong(), anyLong()))
                .willReturn(new ApiResponse<>(ErrorResponse.forbidden().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> customerKeyService.addUserPaymentOption(CommonDataUtils.getNextId(), CommonDataUtils.getNextId(), CommonDataUtils.getNextId()))
                .withNoCause();
    }


    private void assertMapping(final CustomerKeyResponse mockResponse, final CustomerKey customerKey) {
        assertThat(customerKey.getId()).isEqualTo(mockResponse.getId());
        assertThat(customerKey.getCreated().toEpochMilli()).isEqualTo(mockResponse.getCreated());
        assertThat(customerKey.getUpdated().toEpochMilli()).isEqualTo(mockResponse.getUpdated());
        assertThat(customerKey.getValidFrom().toEpochMilli()).isEqualTo(mockResponse.getValidFrom());
        assertThat(customerKey.getValidTo().toEpochMilli()).isEqualTo(mockResponse.getValidTo());
        assertThat(customerKey.getEnabled()).isEqualTo(mockResponse.isIsEnabled());
        assertThat(customerKey.getKeyIdentifier()).isEqualTo(mockResponse.getKeyIdentifier());
        assertThat(customerKey.getName()).isEqualTo(mockResponse.getName());
    }
}