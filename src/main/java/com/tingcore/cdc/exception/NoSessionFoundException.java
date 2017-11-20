package com.tingcore.cdc.exception;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;
import com.tingcore.commons.api.utils.ErrorMessageUtils;

public class NoSessionFoundException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public NoSessionFoundException(final String entityName, final String id) {
      super(ErrorMessageUtils.formatEntityNotFound(entityName, id), DefaultErrorCode.ENTITY_NOT_FOUND);
  }

  public NoSessionFoundException(final String message) {
      super(message, DefaultErrorCode.ENTITY_NOT_FOUND);
  }

  public NoSessionFoundException() {
      super(null, DefaultErrorCode.ENTITY_NOT_FOUND);
  }
}

