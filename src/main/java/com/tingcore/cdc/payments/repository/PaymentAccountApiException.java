package com.tingcore.cdc.payments.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class PaymentAccountApiException extends ExternalApiException {
    public PaymentAccountApiException(final ErrorResponse error) {
        super(error);
    }
}
