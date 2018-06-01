package com.tingcore.cdc.crm.service;

import com.tingcore.commons.rest.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

/**
 * @author palmithor
 * @since 2017-11-22
 */
class PaymentsApiException extends ExternalApiException {

    PaymentsApiException(final ErrorResponse error) {
        super(error);
    }
}
