package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.repository.PaymentOptionsRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.PageResponseUserPaymentOptionResponse;
import com.tingcore.users.model.PaymentOptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    public PageResponse<UserPaymentOption> findUserPaymentOptions(final Long userId) {
        final ApiResponse<PageResponseUserPaymentOptionResponse> apiResponse = paymentOptionsRepository.findUserPaymentOptions(userId);
        return apiResponse.getResponseOptional()
                .map(apiPageResponse -> new PageResponse<>(apiPageResponse.getContent()
                        .stream()
                        .map(UserPaymentOptionMapper::toModel)
                        .collect(Collectors.toList())))
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }
}
