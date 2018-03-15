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

    public String toJson(final Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    public <T> T fromJson(final String json, final Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> T fromJson(final String json, final TypeReference<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public <T> PageResponse<T, String> pageFromJson(final String json, final TypeReference<PageResponse<T, String>> typeReference) throws IOException {
        return mapper.readValue(json, typeReference);
    }
}
