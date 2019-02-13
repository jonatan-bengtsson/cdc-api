package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.external.ExternalApiException;

public class DebtCollectApiException extends ExternalApiException {
    public DebtCollectApiException(final ErrorResponse error) {
        super(error);
    }
}
