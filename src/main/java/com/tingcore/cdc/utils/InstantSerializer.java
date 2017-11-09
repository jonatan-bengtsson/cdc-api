package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;

class InstantSerializer extends JsonSerializer<Instant> {

    @Override
    public void serialize(final Instant value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        final long longValue = value.toEpochMilli();
        gen.writeNumber(longValue);
    }
}
    