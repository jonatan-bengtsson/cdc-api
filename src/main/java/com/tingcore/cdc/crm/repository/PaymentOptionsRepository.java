package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.PaymentOptionsApi;
import com.tingcore.users.model.PageResponseUserPaymentOptionResponse;
import com.tingcore.users.model.PaymentOptionResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public ApiResponse<List<PaymentOptionResponse>> findSupportedPaymentOptions(final Long authorizedUserId) {
        return execute(paymentOptionsApi.getSupportedPaymentOptionsUsingGET(authorizedUserId, authorizedUserId));
    }

    public ApiResponse<PageResponseUserPaymentOptionResponse> findUserPaymentOptions(final Long authorizedUserId) {
        return execute(paymentOptionsApi.getUserPaymentOptionsUsingGET(authorizedUserId, authorizedUserId));
    }
}
