package com.tingcore.cdc.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class GetCardInformationApiException extends ExternalApiException {
    public GetCardInformationApiException(final ErrorResponse error) {
        super(error);
    }
}
