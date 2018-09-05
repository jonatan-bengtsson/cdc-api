package com.tingcore.cdc.crm.exception;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class CampaignServiceException extends ExternalApiException {
    public CampaignServiceException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
