package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class ReceiptApiException extends ExternalApiException {
    public ReceiptApiException(final ErrorResponse error) {
        super(error);
    }
}
