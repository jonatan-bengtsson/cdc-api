package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class PaymentAccountApiException extends ExternalApiException {
    public PaymentAccountApiException(final ErrorResponse error) {
        super(error);
    }
}
