package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.external.ExternalApiException;

public class SessionsApiException extends ExternalApiException {
    public SessionsApiException(final ErrorResponse error) {
        super(error);
    }
}
