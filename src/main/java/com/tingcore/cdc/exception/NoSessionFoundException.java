package com.tingcore.cdc.exception;

import com.tingcore.cdc.constant.ErrorCode;
import com.tingcore.cdc.service.ServiceException;
import com.tingcore.cdc.utils.ErrorMessageUtils;

public class NoSessionFoundException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public NoSessionFoundException(final String entityName, final String id) {
      super(ErrorMessageUtils.formatEntityNotFound(entityName, id), ErrorCode.ENTITY_NOT_FOUND);
  }

  public NoSessionFoundException(final String message) {
      super(message, ErrorCode.ENTITY_NOT_FOUND);
  }

  public NoSessionFoundException() {
      super(null, ErrorCode.ENTITY_NOT_FOUND);
  }
}

