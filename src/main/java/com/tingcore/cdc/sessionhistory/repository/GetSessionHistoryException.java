package com.tingcore.cdc.sessionhistory.repository;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;

public class GetSessionHistoryException extends ServiceException {
    public GetSessionHistoryException() {
        super("An unexpected error occurred while fetching sessions.", DefaultErrorCode.INTERNAL_SERVER_ERROR);
    }
}
