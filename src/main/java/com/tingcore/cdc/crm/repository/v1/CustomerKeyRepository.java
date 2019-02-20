package com.tingcore.cdc.crm.repository.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.cdc.payments.repository.v2.DebtCollectRepository;
import com.tingcore.cdc.payments.repository.v2.DebtTrackerRepository;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v1.CustomerKeyTypesApi;
import com.tingcore.users.api.v1.CustomerKeysApi;
import com.tingcore.users.api.v1.PaymentOptionsApi;
import com.tingcore.users.model.v1.request.UserPaymentOptionIdRequest;
import com.tingcore.users.model.v1.response.CustomerKeyResponse;
import com.tingcore.users.model.v1.response.CustomerKeyTypeResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Repository
public class CustomerKeyRepository extends AbstractUserServiceRepository {

    private final CustomerKeysApi customerKeysApi;
    private final CustomerKeyTypesApi customerKeyTypesApi;
    private final PaymentOptionsApi paymentOptionsApi;

    public CustomerKeyRepository(final ObjectMapper objectMapper,
                                 final CustomerKeysApi customerKeysApi,
                                 final CustomerKeyTypesApi customerKeyTypesApi,
                                 final PaymentOptionsApi paymentOptionsApi) {
        super(objectMapper);
        this.customerKeysApi = customerKeysApi;
        this.customerKeyTypesApi = customerKeyTypesApi;
        this.paymentOptionsApi = paymentOptionsApi;
    }

    public ApiResponse<PageResponse<CustomerKeyResponse>> findByUserId(final Long authorizedUserId) {
        return execute(customerKeysApi.getCustomerKeysByUserId(authorizedUserId, authorizedUserId));
    }

    public ApiResponse<CustomerKeyResponse> findById(final Long authorizedUserId, final Long customerKeyId) {
        return execute(customerKeysApi.getUserCustomerKeysById(authorizedUserId, authorizedUserId, customerKeyId));
    }

    public ApiResponse<List<CustomerKeyTypeResponse>> findCustomerKeyTypes() {
        return execute(customerKeyTypesApi.getCustomerKeyTypes());
    }

    public ApiResponse<CustomerKeyResponse> addUserPaymentOption(Long customerKeyId, UserPaymentOptionIdRequest request, Long authorizedUserId) {
        return execute(paymentOptionsApi.addCustomerKeyPaymentOptions(authorizedUserId, authorizedUserId, customerKeyId, request));
    }

    public ApiResponse<Void> deleteUserPaymentOption(Long customerKeyId, Long paymentOptionId, Long authorizedUserId) {
        return execute(paymentOptionsApi.deleteUserPaymentOptionByUserPaymentOptionId(authorizedUserId, authorizedUserId, customerKeyId, paymentOptionId));
    }
}
