package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.PaymentOptionsRepository;
import com.tingcore.cdc.crm.utils.PaymentOptionDataUtils;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.PaymentOptionResponse;
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
    public void findSupportedPaymentOptions() {
        final PaymentOptionResponse paymentOption = PaymentOptionDataUtils.randomPaymentOption();
        final Long id = CommonDataUtils.getNextId();
        given(paymentOptionRepository.findSupportedPaymentOptions(id)).willReturn(new ApiResponse<>(Collections.singletonList(paymentOption)));
        assertThat(service.findSupportedPaymentOptions(id)).containsExactly(paymentOption);
    }

    @Test
    public void failFindSupportedPaymentOptions() {
        final Long id = CommonDataUtils.getNextId();
        given(paymentOptionRepository.findSupportedPaymentOptions(id)).willReturn(new ApiResponse<>(ErrorResponse.forbidden().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.findSupportedPaymentOptions(id))
                .withNoCause();
    }
}