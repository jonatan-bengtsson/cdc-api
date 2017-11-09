package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;

class InstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(final JsonParser p, final DeserializationContext context) throws IOException {
        final Long epochMilli = p.readValueAs(Long.class);
        return Instant.ofEpochMilli(epochMilli);
    }


}