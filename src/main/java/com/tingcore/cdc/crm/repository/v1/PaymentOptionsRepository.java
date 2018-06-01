package com.tingcore.cdc.crm.repository.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v1.PaymentOptionsApi;
import com.tingcore.users.model.v1.response.UserPaymentOptionResponse;
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
        return execute(paymentOptionsApi.getUserPaymentOptionsByUserId(authorizedUserId, authorizedUserId));
    }
}
