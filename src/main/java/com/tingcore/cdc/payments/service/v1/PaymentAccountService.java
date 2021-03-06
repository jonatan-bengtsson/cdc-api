package com.tingcore.cdc.payments.service.v1;

import com.tingcore.cdc.payments.api.ApiCreateAccountRequest;
import com.tingcore.cdc.payments.repository.v1.PaymentAccountRepository;
import com.tingcore.payments.model.*;
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

    public Ok createElwinContract(final String elwinId, final String keyIdentifier) {
        return paymentAccountRepository.createElwinContract(elwinId, keyIdentifier);
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

    public List<ApiElwinCustomerContract> getElwinContracts(final String elwinId) {
        return paymentAccountRepository.getElwinContracts(elwinId);
    }

    public ApiElwinCustomerContract getElwinContract(final String elwinId, final String keyIdentifier) {
        return paymentAccountRepository.getElwinContract(elwinId, keyIdentifier);
    }
}
