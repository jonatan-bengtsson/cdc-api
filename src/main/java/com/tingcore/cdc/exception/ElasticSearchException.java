package com.tingcore.cdc.exception;

public class ElasticSearchException extends RuntimeException {
    public ElasticSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
