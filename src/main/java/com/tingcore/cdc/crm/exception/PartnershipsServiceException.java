package com.tingcore.cdc.crm.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class PartnershipsServiceException extends ExternalApiException {
    public PartnershipsServiceException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
