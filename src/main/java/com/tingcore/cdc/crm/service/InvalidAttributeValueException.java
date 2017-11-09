package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.constant.ErrorCode;
import com.tingcore.cdc.service.ServiceException;
import com.tingcore.cdc.utils.ErrorMessageUtils;

/**
 * @author palmithor
 * @since 2017-11-09
 */
public class InvalidAttributeValueException extends ServiceException {


    InvalidAttributeValueException(final Long attributeValueId) {
        super(ErrorMessageUtils.formatInvalidAttributeValue(attributeValueId), ErrorCode.DATA_PROCESSING_ERROR);
    }
}
