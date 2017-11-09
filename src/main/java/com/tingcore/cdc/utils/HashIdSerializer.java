package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tingcore.cdc.service.HashIdService;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author palmithor
 * @since 2017-10-30
 */
@Component
public class HashIdSerializer extends JsonSerializer<Long> {

    private final HashIdService hashIdService;

    public HashIdSerializer(final HashIdService hashIdService) {
        this.hashIdService = hashIdService;
    }

    @Override
    public void serialize(final Long aLong, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        String currentKey = jsonGenerator.getOutputContext().getCurrentName();
        if(currentKey == null) { // if current key is null, the value is a list
            currentKey = jsonGenerator.getOutputContext().getParent().getCurrentName();
        }

        if (HashIdService.isIdKey(currentKey)) {
            jsonGenerator.writeString(hashIdService.encode(aLong));
        } else {
            jsonGenerator.writeNumber(aLong);
        }
    }
}
