package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.repository.v1.PaymentOptionsRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.utils.PaymentOptionDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v1.response.UserPaymentOptionResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


/**
 * @author palmithor
 * @since 2017-12-15
 */
@RunWith(SpringRunner.class)
public class PaymentOptionServiceTest {

    @MockBean private PaymentOptionsRepository paymentOptionRepository;

    private PaymentOptionService service;

    @Before
    public void setUp() {
        this.service = new PaymentOptionService(paymentOptionRepository);
    }

    @Test
    public void findUserPaymentOptions() {
        final Long userId = CommonDataUtils.getNextId();
        final UserPaymentOptionResponse mockResponse = PaymentOptionDataUtils.randomUserPaymentOptionResponse();
        final PageResponse<UserPaymentOptionResponse> mockPageResponse = new PageResponse<>(Collections.singletonList(mockResponse));
        given(paymentOptionRepository.findUserPaymentOptions(userId)).willReturn(new ApiResponse<>(mockPageResponse));
        final PageResponse<UserPaymentOption> results = service.findUserPaymentOptions(userId);
        assertThat(results.getPagination()).isNull();
        assertThat(results.getContent()).hasSize(1);
        assertThat(results.getContent().get(0).getId()).isEqualTo(mockResponse.getId());
    }

    @Test
    public void failFindUserPaymentOptionsApiError() {
        final Long userId = CommonDataUtils.getNextId();
        given(paymentOptionRepository.findUserPaymentOptions(userId)).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        Assertions.assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.findUserPaymentOptions(userId))
                .withNoCause();
    }
}
