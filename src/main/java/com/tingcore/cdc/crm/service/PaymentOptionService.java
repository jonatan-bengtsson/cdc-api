package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.repository.PaymentOptionsRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.model.UserPaymentOptionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author palmithor
 * @since 2017-12-15
 */
@Service
public class PaymentOptionService {

    private final PaymentOptionsRepository paymentOptionsRepository;

    PaymentOptionService(final PaymentOptionsRepository paymentOptionsRepository) {
        this.paymentOptionsRepository = paymentOptionsRepository;
    }


    public PageResponse<UserPaymentOption> findUserPaymentOptions(final Long userId) {
        final ApiResponse<PageResponse<UserPaymentOptionResponse>> apiResponse = paymentOptionsRepository.findUserPaymentOptions(userId);
        return apiResponse.getResponseOptional()
                .map(apiPageResponse -> {
                    final List<UserPaymentOption> content = apiPageResponse.getContent()
                            .stream()
                            .map(UserPaymentOptionMapper::toModel)
                            .collect(Collectors.toList());
                    return new PageResponse<>(content, apiPageResponse.getPagination());
                })
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }
}
