package com.tingcore.cdc.service;


import com.tingcore.cdc.constant.ErrorCode;

/**
 * @author palmithor
 * @since 2017-09-12
 */
public class ServiceException extends RuntimeException {

    private final ErrorCode errorCode;

    public ServiceException(final String message, final ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
