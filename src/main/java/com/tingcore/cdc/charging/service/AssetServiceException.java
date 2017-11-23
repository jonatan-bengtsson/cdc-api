package com.tingcore.cdc.charging.service;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class AssetServiceException extends ExternalApiException {
    public AssetServiceException(ErrorResponse e) {
        super(e);
    }
}
