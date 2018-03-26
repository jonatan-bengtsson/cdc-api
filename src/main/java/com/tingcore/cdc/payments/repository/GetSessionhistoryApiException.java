package com.tingcore.cdc.payments.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class GetSessionhistoryApiException extends ExternalApiException {
    public GetSessionhistoryApiException(final ErrorResponse error) {
        super(error);
    }
}
