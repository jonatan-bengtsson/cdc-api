package com.tingcore.cdc.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author palmithor
 * @since 2017-09-14
 */
public class JsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {

    }

    /**
     * Convert map of key value strings to json string
     *
     * @param map the map to convert
     * @return A JSON string if conversion was successful, otherwise null
     */
    public static String stringMapToJson(final Map<String, String> map) {
        String str = null;
        if (map != null) {
            try {
                str = getObjectMapperBuilder().build().writeValueAsString(map);
            } catch (final JsonProcessingException e) {
                LOG.error("Unable to convert a map to json", e); // shouldn't happen
            }
        }
        return str;
    }

    /**
     * Convert json string to Map<String, String>
     *
     * @param json to convert
     * @return A map with the json key value pairs if successful, otherwise a empty map
     */
    public static Map<String, String> jsonToStringMap(final String json) {
        Map<String, String> map = new HashMap<>(0);
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        try {
            map = getObjectMapperBuilder().build().readValue(json, typeRef);
        } catch (IOException e) {
            LOG.error("Unable to convert a map to json", e); // shouldn't happen
        }
        return map;
    }

    public static Jackson2ObjectMapperBuilder getObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.failOnUnknownProperties(true);
        builder.failOnEmptyBeans(false); // todo remove this when pagination has been decided and implemented.
        builder.serializerByType(Instant.class, new InstantSerializer());
        builder.deserializerByType(Instant.class, new InstantDeserializer());

        return builder;
    }
}
