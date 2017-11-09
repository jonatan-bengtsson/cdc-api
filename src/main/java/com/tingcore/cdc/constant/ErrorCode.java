package com.tingcore.cdc.constant;

/**
 * @author palmithor
 * @since 2017-09-07
 */
public enum ErrorCode {

    // 400*
    VALIDATION_FAILED(40001, "error.validation_failed"),
    MALFORMED_REQUEST(40002, "error.malformed_request"),
    INVALID_TYPE(40003, "error.invalid_type"),
    MISSING_REQUIRED_QUERY_PARAM(40004, "error.missing_required_query_param"),
    INVALID_SORT_PARAM(40005, "error.unsupported_sort_param"),
    UNSUPPORTED_MEDIA_TYPE(40006, "error.unsupported_media_type"),

    // 403*
    FORBIDDEN(40300, "error.forbidden"),

    // 404*
    URL_NOT_FOUND(40400, "error.resource_not_found"),
    ENTITY_NOT_FOUND(40401, "error.entity_not_found"),

    // 405*
    METHOD_NOT_ALLOWED(40500, "error.method_not_allowed"),

    // 409*
    CONFLICT_DATA(40900, "error.conflict_already_existing_data"),
    CONFLICT_VERSION(40901, "error.conflict_version"), // used for PUT if version check fails

    INTERNAL_SERVER_ERROR(50000, "error.internal_server_error"),
    DATA_PROCESSING_ERROR(50001, "error.data_processing_error"),
    ;

    private Integer value;
    private String messageKey;

    ErrorCode(final Integer value, final String messageKey) {
        this.value = value;
        this.messageKey = messageKey;
    }

    public Integer value() {
        return value;
    }

    public String messageKey() {
        return messageKey;
    }
}
