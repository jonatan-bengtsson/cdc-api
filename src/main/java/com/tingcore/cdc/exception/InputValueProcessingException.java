package com.tingcore.cdc.exception;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;

public class InputValueProcessingException extends ServiceException {


    public InputValueProcessingException(final String attributeValueId) {
        super(String.format("Could not process input with value %s", attributeValueId), DefaultErrorCode.DATA_PROCESSING_ERROR);
    }
}
