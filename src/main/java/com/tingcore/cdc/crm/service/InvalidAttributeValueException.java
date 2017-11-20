package com.tingcore.cdc.crm.service;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;
import com.tingcore.commons.api.utils.ErrorMessageUtils;

/**
 * @author palmithor
 * @since 2017-11-09
 */
public class InvalidAttributeValueException extends ServiceException {


    InvalidAttributeValueException(final Long attributeValueId) {
        super(ErrorMessageUtils.formatInvalidAttributeValue(attributeValueId), DefaultErrorCode.DATA_PROCESSING_ERROR);
    }
}
