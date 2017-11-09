package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.tingcore.cdc.service.HashIdService;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author palmithor
 * @since 2017-10-30
 */
@Component
public class HashIdDeserializer extends JsonDeserializer<Long> {


    private final HashIdService hashIdService;

    public HashIdDeserializer(final HashIdService hashIdService) {
        this.hashIdService = hashIdService;
    }

    @Override
    public Long deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        String currentKey = jsonParser.getParsingContext().getCurrentName();
        if(currentKey == null) { // if current key is null, the value is a list
            currentKey = jsonParser.getParsingContext().getParent().getCurrentName();
        }

        if (HashIdService.isIdKey(currentKey)) {
            return hashIdService.decode(jsonParser.getValueAsString()).orElse(null);
        }
        return jsonParser.getLongValue();
    }
}