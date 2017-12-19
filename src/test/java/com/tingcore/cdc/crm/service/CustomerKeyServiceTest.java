package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.CustomerKeyRepository;
import com.tingcore.cdc.crm.response.CustomerKey;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.PageResponseCustomerKeyResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

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
        customerKeyService = new CustomerKeyService(customerKeyRepository);
    }

    @Test
    public void findByUserId() {
        final Long userId = CommonDataUtils.getNextId();
        final PageResponseCustomerKeyResponse mockResponse = CustomerKeyDataUtils.randomPageResponse();
        given(customerKeyRepository.findByUserId(userId)).willReturn(new ApiResponse<>(mockResponse));
        final PageResponse<CustomerKey> response = customerKeyService.findByUserId(userId);
        assertThat(response.getContent()).hasSize(mockResponse.getContent().size());
        response.getContent().forEach(customerKey -> {
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
}