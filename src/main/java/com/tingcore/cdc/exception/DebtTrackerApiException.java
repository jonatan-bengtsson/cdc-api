package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.external.ExternalApiException;

public class DebtTrackerApiException extends ExternalApiException {
    public DebtTrackerApiException(final ErrorResponse error) {
        super(error);
    }
}
