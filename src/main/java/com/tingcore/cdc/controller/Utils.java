package com.tingcore.cdc.controller;

import com.tingcore.commons.api.repository.ApiResponse;

import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.ErrorResponse;

import java.util.function.Function;

public class Utils {
    public static <T, E extends ExternalApiException> T getResponseOrThrowError(ApiResponse<T> response, Function<ErrorResponse, E> exceptionCreator) throws E {
        return response
                .getResponseOptional()
                .orElseThrow(() -> exceptionCreator.apply(response.getError()));
    }
}