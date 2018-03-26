package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.rest.PageResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author palmithor
 * @since 2017-04-28.
 */
@Component
public class MockMvcUtils {

    private final ObjectMapper mapper;

    private MockMvcUtils(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toJson(final Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(final String json, final Class<T> className) {
        try {
            return mapper.readValue(json, className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(final String json, final TypeReference<T> className) {
        try {
            return mapper.readValue(json, className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> PageResponse<T> pageFromJson(final String json, final TypeReference<PageResponse<T>> typeReference) {
        try {

            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

