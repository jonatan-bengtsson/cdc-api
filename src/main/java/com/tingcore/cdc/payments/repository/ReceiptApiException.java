package com.tingcore.cdc.payments.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class ReceiptApiException extends ExternalApiException {
    public ReceiptApiException(final ErrorResponse error) {
        super(error);
    }
}
