package com.tingcore.cdc.payments.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class GetCardInformationApiException extends ExternalApiException {
    public GetCardInformationApiException(final ErrorResponse error) {
        super(error);
    }
}
