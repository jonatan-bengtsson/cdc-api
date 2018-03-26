package com.tingcore.cdc.exception;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;

public class SessionHistoryFailureException extends ServiceException {

    public SessionHistoryFailureException(final String id) {
        super(String.format("Failure to parse id: %s", id), DefaultErrorCode.INVALID_SORT_PARAM);
    }
}

