package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.PaymentOptionsApi;
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

    public ApiResponse<List<PaymentOptionResponse>> findSupportedPaymentOptions(final Long userId) {
        return execute(paymentOptionsApi.getSupportedPaymentOptionsUsingGET(userId, userId));
    }
}
