package com.tingcore.cdc.charging.repository;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class ChargingSessionApiException extends ExternalApiException{
    public ChargingSessionApiException(final ErrorResponse error) {
        super(error);
    }
}
