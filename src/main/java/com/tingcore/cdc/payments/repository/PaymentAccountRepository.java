package com.tingcore.cdc.payments.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.payments.cpo.api.PaymentaccountsApi;
import com.tingcore.payments.cpo.model.ApiDeletedCustomer;
import com.tingcore.payments.cpo.model.ApiPaymentAccount;
import com.tingcore.payments.cpo.model.CreateAccountRequest;
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

    public String createStripeCustomer(final String cardSource,
                                       final Long organizationID) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.createStripeCustomer(cardSource, organizationID));
    }

    public ApiPaymentAccount getAccount(final String paymentAccountId) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getUserAccount(paymentAccountId));
    }

    public ApiDeletedCustomer deleteUserAccount(final Long userId,
                                                final String paymentOption) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.deleteUserAccount_0(userId, paymentOption));
    }

    public List<ApiPaymentAccount> getAllAccountsById(final Long keyId,
                                                      final Long userId) {
        return getResponseOrPaymentAccountError(paymentaccountsApi.getUserPaymentAccounts(keyId, userId));
    }

    private <T, E extends ExternalApiException> T getResponseOrPaymentAccountError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), PaymentAccountApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
