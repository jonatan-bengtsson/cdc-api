package com.tingcore.cdc.utils;

import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * @author moa.mackegard
 * @since 2017-11-17.
 */
public class ErrorBodyMatcher {

    private ErrorBodyMatcher() {

    }

    public static ResultMatcher entityNotFoundMatcher(final String entitySimpleName) {
        return content().string("{\"statusCode\":404,\"status\":\"Not Found\",\"code\":40401,\"message\":\"Entity not found\",\"details\":[\"" + entitySimpleName + " not found\"]}");
    }

    public static ResultMatcher entityNotFoundMatcher(final String entitySimpleName, final String id) {
        return content().string("{\"statusCode\":404,\"status\":\"Not Found\",\"code\":40401,\"message\":\"Entity not found\",\"details\":[\"" + entitySimpleName + " defined by '" + id + "' does not exist\"]}");
    }

    public static ResultMatcher validationFailedMatcher(String fieldName, String rejectedValue, String message) {
        return content().string("{" +
                "\"statusCode\":400," +
                "\"status\":\"Bad Request\"," +
                "\"code\":40001," +
                "\"message\":\"Validation failed\"," +
                "\"fieldViolations\":[" +
                "{" +
                "\"field\":\"" + fieldName + "\"," +
                "\"rejectedValue\":\"" + rejectedValue + "\"," +
                "\"message\":\"" + message + "\"" +
                "}" +
                "]" +
                "}");
    }
}
