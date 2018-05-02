package com.tingcore.cdc.payments.service;

import com.tingcore.cdc.payments.api.ApiCreateAccountRequest;
import com.tingcore.cdc.payments.repository.PaymentAccountRepository;
import com.tingcore.payments.cpo.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;

    public PaymentAccountService(final PaymentAccountRepository paymentAccountRepository) {
        this.paymentAccountRepository = paymentAccountRepository;
    }

    public ApiPaymentAccount createUserAccount(final Long userId,
                                               final ApiCreateAccountRequest request) {
        CreateAccountRequest req = new CreateAccountRequest();
        Account account = new Account();
        account.setPaymentMethod(request.getAccount().getPaymentMethod());
        account.setAccountOwnerId(userId);
        account.setData(request.getAccount().getData());
        req.setAccount(account);
        return paymentAccountRepository.createUserAccount(req);
    }

    public String createStripeCustomer(final String cardSource,
                                       final Long organizationId) {
        return paymentAccountRepository.createStripeCustomer(cardSource, organizationId);
    }

    public ApiPaymentAccount getAccount(final String paymentOptionReference) {
        return paymentAccountRepository.getAccount(paymentOptionReference);
    }

    public List<ApiPaymentAccount> getAllAccountsById(final Long keyId,
                                                      final Long userId) {
        return paymentAccountRepository.getAllAccountsById(keyId, userId);
    }

    public ApiDeletedCustomer deleteUserAccount(final Long userId,
                                                final String paymentOption) {
        return paymentAccountRepository.deleteUserAccount(userId, paymentOption);
    }
}