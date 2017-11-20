package com.tingcore.cdc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.service.MessageByLocaleService;
import com.tingcore.commons.api.service.ErrorCode;
import com.tingcore.commons.rest.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author palmithor
 * @since 2017-10-24
 */
@Component
class FilterUtils {

    private final MessageByLocaleService messageByLocaleService;
    private final ObjectMapper objectMapper;

    FilterUtils(final MessageByLocaleService messageByLocaleService, final ObjectMapper objectMapper) {
        this.messageByLocaleService = messageByLocaleService;
        this.objectMapper = objectMapper;
    }

    void setError(final HttpServletResponse response, final ErrorResponse.Builder errorResponseBuilder, final ErrorCode errorCode, String... details) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        final ErrorResponse errorResponse = errorResponseBuilder
                .code(errorCode.value())
                .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                .details(Arrays.asList(details))
                .build();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.setStatus(errorResponse.getStatusCode());
    }

    void setError(final HttpServletResponse response, final ErrorResponse error) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(error));
        response.setStatus(error.getStatusCode());
    }
}
