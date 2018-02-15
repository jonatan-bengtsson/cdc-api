package com.tingcore.cdc.payments.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class GetPaymentAccountApiException extends ExternalApiException {
    public GetPaymentAccountApiException(final ErrorResponse error) {
        super(error);
    }
}
