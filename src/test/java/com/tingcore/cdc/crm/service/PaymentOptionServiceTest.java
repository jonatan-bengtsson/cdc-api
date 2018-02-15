package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.repository.PaymentOptionsRepository;
import com.tingcore.cdc.crm.utils.PaymentOptionDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.PageResponseUserPaymentOptionResponse;
import com.tingcore.users.model.PaymentOptionResponse;
import com.tingcore.users.model.UserPaymentOptionResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
        final PageResponseUserPaymentOptionResponse mockPageResponse = new PageResponseUserPaymentOptionResponse();
        mockPageResponse.setContent(Collections.singletonList(mockResponse));
        given(paymentOptionRepository.findUserPaymentOptions(userId)).willReturn(new ApiResponse<>(mockPageResponse));
        final PageResponse<UserPaymentOption> results = service.findUserPaymentOptions(userId);
        Assertions.assertThat(results).hasNoNullFieldsOrProperties();
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