package com.tingcore.cdc.charging.repository;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class AssetPaymentsApiException extends ExternalApiException{
    public AssetPaymentsApiException(final ErrorResponse error) {
        super(error);
    }
}
