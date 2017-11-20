package com.tingcore.cdc.exception;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;
import com.tingcore.commons.api.utils.ErrorMessageUtils;

/**
 * @author palmithor
 * @since 2017-09-14
 */
public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(final String entityName, final String id) {
        super(ErrorMessageUtils.formatEntityNotFound(entityName, id), DefaultErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(final String message) {
        super(message, DefaultErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException() {
        super(null, DefaultErrorCode.ENTITY_NOT_FOUND);
    }
}

