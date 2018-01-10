package com.tingcore.cdc.charging.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class PriceApiException extends ExternalApiException{
    public PriceApiException(final ErrorResponse error) {
        super(error);
    }
}
