package com.tingcore.cdc.payments.repository.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.PaymentAccountApiException;
import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.payments.api.PaymentaccountsApi;
import com.tingcore.payments.model.*;
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

        return getResponseOrPaymentAccountError(paymentaccountsApi.createUserAccount(request));
    }

    public Ok createElwinContract(final String elwinId, final String keyIdentifier) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.createElwinContract(elwinId, keyIdentifier));
    }

    public String createStripeCustomer(final String cardSource,
                                       final Long organizationID) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.createStripeCustomer(cardSource, organizationID));
    }

    public ApiPaymentAccount getAccount(final String paymentAccountId) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getUserAccount(paymentAccountId));
    }

    public ApiDeletedCustomer deleteUserAccount(final Long userId,
                                                final String paymentOption) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.deleteUserAccount(userId, paymentOption));
    }

    public List<ApiPaymentAccount> getAllAccountsById(final Long keyId,
                                                      final Long userId) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getUserPaymentAccounts(keyId, userId));
    }

    public List<ApiElwinCustomerContract> getElwinContracts(final String elwinId) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getElwinCustomers(elwinId));
    }

    public ApiElwinCustomerContract getElwinContract(final String elwinId, final String keyIdentifier) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getElwinCustomer(elwinId, keyIdentifier));
    }

    private <T, E extends ExternalApiException> T getResponseOrPaymentAccountError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), PaymentAccountApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
