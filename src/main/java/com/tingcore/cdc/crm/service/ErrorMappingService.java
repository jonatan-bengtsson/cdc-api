package com.tingcore.cdc.crm.service;

import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author palmithor
 * @since 2018-02-06
 */
public class ErrorMappingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorMappingService.class);
    private static final Pattern ENTITY_NOT_FOUND_ID_PATTERN = Pattern.compile("(\\w+) defined by '(\\d+)' does not exist");
    private final HashIdService hashIdService;

    public ErrorMappingService(final HashIdService hashIdService) {
        this.hashIdService = hashIdService;
    }

    public ErrorResponse prepareErrorResponse(final ErrorResponse errorResponse) {
        ErrorResponse.Builder builder = ErrorResponse.copy(errorResponse);
        try {
            Optional.ofNullable(errorResponse.getDetails())
                    .ifPresent(errorDetails -> {
                        builder.details(errorDetails.stream().map(detail -> {
                            final Matcher matcher = ENTITY_NOT_FOUND_ID_PATTERN.matcher(detail);
                            if (matcher.find()) {
                                return buildDetail(detail);
                            }
                            return detail;
                        }).collect(Collectors.toList()));
                    });
        } catch (Exception e) {
            LOGGER.warn("An unexpected error occurred when mapping user service error response", e);
        }
        return builder.build();
    }

    private String buildDetail(final String detail) {
        final Long id = Long.valueOf(detail.substring(detail.indexOf('\'') + 1, detail.lastIndexOf('\'')));
        final String entity = detail.substring(0, detail.indexOf(StringUtils.SPACE));
        return entity + " defined by '" + hashIdService.encode(id) + "' does not exist";
    }

}
