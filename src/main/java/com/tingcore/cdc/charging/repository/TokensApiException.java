package com.tingcore.cdc.charging.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class TokensApiException extends ExternalApiException {
    public TokensApiException(final ErrorResponse error) {
        super(error);
    }
}
