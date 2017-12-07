package com.tingcore.cdc.exception;

import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;
import com.tingcore.commons.api.utils.ErrorMessageUtils;

public class NoPriceFoundException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public NoPriceFoundException(final String entityName, final String id) {
      super(ErrorMessageUtils.formatEntityNotFound(entityName, id), DefaultErrorCode.ENTITY_NOT_FOUND);
  }

  public NoPriceFoundException(final String message) {
      super(message, DefaultErrorCode.ENTITY_NOT_FOUND);
  }

  public NoPriceFoundException() {
      super(null, DefaultErrorCode.ENTITY_NOT_FOUND);
  }
}

