package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.users.api.PaymentOptionsApi;
import com.tingcore.users.model.PageResponseUserPaymentOptionResponse;
import com.tingcore.users.model.UserPaymentOptionResponse;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@Repository
public class PaymentOptionsRepository extends AbstractUserServiceRepository {

    private final PaymentOptionsApi paymentOptionsApi;

    public PaymentOptionsRepository(final ObjectMapper objectMapper, final PaymentOptionsApi paymentOptionsApi) {
        super(objectMapper);
        this.paymentOptionsApi = paymentOptionsApi;
    }

    public ApiResponse<PageResponse<UserPaymentOptionResponse>> findUserPaymentOptions(final Long authorizedUserId) {
        final ApiResponse<PageResponseUserPaymentOptionResponse> apiResponse = execute(paymentOptionsApi.getUserPaymentOptionsUsingGET(authorizedUserId, authorizedUserId));

        return apiResponse.getResponseOptional()
                .map(response -> new ApiResponse<>(new PageResponse<>(response.getContent(), convertGeneratedPagination(response.getPagination()))))
                .orElse(new ApiResponse<>(apiResponse.getError()));
    }
}
