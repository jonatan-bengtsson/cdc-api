package com.tingcore.cdc.crm.model;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

public class CampaignServiceException extends ExternalApiException {
    public CampaignServiceException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
