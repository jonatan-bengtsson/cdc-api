package com.tingcore.cdc.exception;

import com.tingcore.cdc.constant.ErrorCode;
import com.tingcore.cdc.service.ServiceException;
import com.tingcore.cdc.utils.ErrorMessageUtils;

/**
 * @author palmithor
 * @since 2017-09-14
 */
public class EntityNotFoundException extends ServiceException {

    public EntityNotFoundException(final String entityName, final String id) {
        super(ErrorMessageUtils.formatEntityNotFound(entityName, id), ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(final String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException() {
        super(null, ErrorCode.ENTITY_NOT_FOUND);
    }
}

