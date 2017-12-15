package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.repository.PaymentOptionsRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.model.PaymentOptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author palmithor
 * @since 2017-12-15
 */
@Service
public class PaymentOptionService {

    private final PaymentOptionsRepository paymentOptionsRepository;

    public PaymentOptionService(final PaymentOptionsRepository paymentOptionsRepository) {
        this.paymentOptionsRepository = paymentOptionsRepository;
    }

    public List<PaymentOptionResponse> findSupportedPaymentOptions(final Long userId) {
        final ApiResponse<List<PaymentOptionResponse>> apiResponse = paymentOptionsRepository.findSupportedPaymentOptions(userId);
        return apiResponse
                .getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

}
