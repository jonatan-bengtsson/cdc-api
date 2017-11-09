package com.tingcore.cdc.utils;

/**
 * @author palmithor
 * @since 2017-10-24
 */
public class ErrorMessageUtils {

    private ErrorMessageUtils() {

    }

    public static String formatEntityNotFound(final String entityName, final String id) {
        return String.format("%s defined by '%s' does not exist", entityName, id);
    }

    public static String formatInvalidAttributeValue(final Long attributeValueId) {
        return String.format("Attribute value defined by '%d' has invalid format", attributeValueId);
    }
}
