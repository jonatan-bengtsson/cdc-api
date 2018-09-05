package com.tingcore.cdc.crm.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class CustomerKeyOrderServiceException extends ExternalApiException {
    public CustomerKeyOrderServiceException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
