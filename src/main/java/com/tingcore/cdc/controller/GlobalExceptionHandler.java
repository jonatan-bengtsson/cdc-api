package com.tingcore.cdc.controller;


import com.tingcore.cdc.charging.service.AssetServiceException;
import com.tingcore.cdc.constant.ErrorCode;
import com.tingcore.cdc.crm.service.ErrorMappingService;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.crm.service.v1.InvalidAttributeValueException;
import com.tingcore.cdc.exception.ElasticSearchException;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.exception.InputValueProcessingException;
import com.tingcore.cdc.exception.PaymentAccountApiException;
import com.tingcore.cdc.service.MessageByLocaleService;
import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.ServiceException;
import com.tingcore.commons.core.utils.StreamUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.external.ExternalApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * @author palmithor
 * @since 2017-08-31
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageByLocaleService messageByLocaleService;
    private final ErrorMappingService errorMappingService;

    @Autowired
    public GlobalExceptionHandler(final MessageByLocaleService messageByLocaleService, final ErrorMappingService errorMappingService) {
        this.messageByLocaleService = messageByLocaleService;
        this.errorMappingService = errorMappingService;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception e) {
        LOG.warn(e.getMessage(), e);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return errorResponseToResponseEntity(ErrorResponse
                                                     .serverError()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .build());
    }


    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
        return errorResponseToResponseEntity(ErrorResponse.badRequest()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .addFieldViolation(e)
                                                     .build());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
        return errorResponseToResponseEntity(ErrorResponse.badRequest()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .addFieldViolation(e.getName(), e.getValue(), messageByLocaleService.getMessage(ErrorCode.INVALID_TYPE.messageKey()))
                                                     .build());
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(final HttpMediaTypeNotSupportedException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.UNSUPPORTED_MEDIA_TYPE;
        return errorResponseToResponseEntity(ErrorResponse.badRequest()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .build());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
        return errorResponseToResponseEntity(ErrorResponse.methodNotAllowed()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(final MethodArgumentNotValidException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        final ErrorResponse.Builder builder = ErrorResponse.badRequest()
                .code(errorCode.value())
                .message(messageByLocaleService.getMessage(errorCode.messageKey()));
        if (e.getBindingResult().getFieldErrorCount() > 0 || e.getBindingResult().getGlobalErrorCount() == 0) {
            StreamUtils.asStream(fieldErrors.iterator())
                    .forEach(fieldError -> builder.addFieldViolation(fieldError.getField(),
                                                                     fieldError.getRejectedValue(),
                                                                     fieldError.getDefaultMessage()));

            return errorResponseToResponseEntity(builder.build());
        } else {
            return errorResponseToResponseEntity(builder.addDetail(e.getBindingResult().getGlobalError().getDefaultMessage()).build());
        }
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyMissing(final HttpMessageNotReadableException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.MALFORMED_REQUEST;
        return errorResponseToResponseEntity(ErrorResponse.badRequest()
                                                     .code(errorCode.value())
                                                     .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                                                     .build());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleRequestBodyMissing(final MissingServletRequestParameterException e) {
        LOG.trace(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.MISSING_REQUIRED_QUERY_PARAM;
        return errorResponseToResponseEntity(
                ErrorResponse.badRequest()
                        .code(errorCode.value())
                        .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                        .addFieldViolation(e.getParameterName(), null, "")
                        .build());
    }

    @ExceptionHandler(value = AssetServiceException.class)
    public ResponseEntity<ErrorResponse> handleAssetServiceException(final AssetServiceException e) {
        LOG.debug(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return errorResponseToResponseEntity(
                ErrorResponse.badRequest()
                        .code(errorCode.value())
                        .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                        .build()
        );
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final EntityNotFoundException e) {
        return handleServiceException(e, ErrorResponse.notFound());
    }

    @ExceptionHandler(value = InputValueProcessingException.class)
    public ResponseEntity<ErrorResponse> handleInputValueProcessingException(final InputValueProcessingException e) {
        return handleServiceException(e, ErrorResponse.notFound());
    }

    @ExceptionHandler(value = ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiException(final ExternalApiException e) {
        LOG.debug(e.getMessage(), e);
        if (e instanceof UsersApiException) {
            return errorResponseToResponseEntity(errorMappingService.prepareErrorResponse(e.getErrorResponse()));
        } else if (e instanceof PaymentAccountApiException) {
            return errorResponseToResponseEntity(errorMappingService.prepareErrorResponse(e.getErrorResponse()));
        }
        return errorResponseToResponseEntity(e.getErrorResponse());
    }

    @ExceptionHandler(value = ElasticSearchException.class)
    public ResponseEntity<ErrorResponse> handleElasticSearchException(final ElasticSearchException e) {
        LOG.error(e.getMessage(), e);
        final ErrorCode errorCode = ErrorCode.DATA_PROCESSING_ERROR;
        return errorResponseToResponseEntity(
                ErrorResponse.serverError()
                        .code(errorCode.value())
                        .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                        .build());
    }

    @ExceptionHandler(value = InvalidAttributeValueException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final InvalidAttributeValueException e) {
        LOG.warn("Data was invalid for some attribute value - please check!", e);
        return handleServiceException(e, ErrorResponse.serverError());
    }

    private ResponseEntity<ErrorResponse> handleServiceException(final ServiceException serviceException, final ErrorResponse.Builder errorResponseBuilder) {
        LOG.trace(serviceException.getMessage(), serviceException);
        errorResponseBuilder
                .code(serviceException.getErrorCode().value())
                .message(messageByLocaleService.getMessage(serviceException.getErrorCode().messageKey()));
        if (StringUtils.isNotBlank(serviceException.getMessage())) {
            errorResponseBuilder.addDetail(serviceException.getMessage());
        }
        return errorResponseToResponseEntity(errorResponseBuilder.build());
    }

    private ResponseEntity<ErrorResponse> errorResponseToResponseEntity(final ErrorResponse errorResponse) {
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
}
