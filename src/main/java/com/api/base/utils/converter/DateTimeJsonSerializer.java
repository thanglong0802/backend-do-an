package com.api.base.utils.converter;

import com.api.base.utils.Constants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class DateTimeJsonSerializer extends JsonSerializer<Instant> {

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = fmt.format(value);
        gen.writeString(str);
    }
}