package com.tingcore.cdc.sessionhistory.repository;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class GetSessionHistoryApiException extends ExternalApiException {
    public GetSessionHistoryApiException(final ErrorResponse error) {
        super(error);
    }
}
