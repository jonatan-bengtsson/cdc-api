package com.tingcore.cdc.payments.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.payments.emp.api.PaymentaccountsApi;
import com.tingcore.payments.emp.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class PaymentAccountRepository extends AbstractApiRepository {
    private final PaymentaccountsApi paymentaccountsApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public PaymentAccountRepository(final ObjectMapper objectMapper,
                                    final PaymentaccountsApi paymentaccountsApi) {
        super(notNull(objectMapper));
        this.paymentaccountsApi = paymentaccountsApi;
    }

    public ApiPaymentAccount createUserAccount(final CreateAccountRequest request) {

        return GetResponseOrPaymentAccountError(paymentaccountsApi.createUserAccount(request));
    }

    public ApiPaymentAccount getAccount(final String paymentAccountId) {
        return GetResponseOrPaymentAccountError(paymentaccountsApi.getUserAccount(paymentAccountId));
    }

    public ApiDeletedCustomer deleteUserAccount(final DeleteAccountRequest request) {
        return GetResponseOrPaymentAccountError(paymentaccountsApi.deleteUserAccount(request));
    }

    public List<ApiPaymentAccount> getAllAccountsById(final Long keyId,
                                                      final Long userId) {
        return GetResponseOrPaymentAccountError(paymentaccountsApi.getUserPaymentAccounts(keyId, userId));
    }

    public ApiCard getCardInformation(final String stripeId){
        return GetResponseOrCardInformationError(paymentaccountsApi.getCardInformation(stripeId));
    }

    private <T, E extends ExternalApiException> T GetResponseOrPaymentAccountError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), GetPaymentAccountApiException::new);
    }

    private <T, E extends ExternalApiException> T GetResponseOrCardInformationError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), GetCardInformationApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
