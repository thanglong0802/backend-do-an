package com.api.base.utils.converter;

import com.api.base.utils.Constants;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.exception.BusinessException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeJsonDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DateTimeFormatter fmt;
        Instant result;
        try {
            fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_WITH_ZONE).withZone(Constants.UTC_ZONE_ID);
            result = Instant.from(fmt.parse(p.getText()));
        } catch (Exception e) {
            try {
                fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_HYPHEN).withZone(Constants.UTC_ZONE_ID);
                result = Instant.from(fmt.parse(p.getText()));
            } catch (Exception ex) {
                fmt = DateTimeFormatter.ofPattern(Constants.SHORT_DATETIME_FORMAT_HYPHEN).withZone(Constants.UTC_ZONE_ID);
                result = Instant.from(fmt.parse(p.getText()));
            }
        }

        if (Objects.nonNull(result) && (result.isAfter(Constants.MAX_DATE) || result.isBefore(Constants.MIN_DATE))) {
            throw new BusinessException(MessageCode.ERR_DATE.name(), MessageCode.ERR_DATE, p.getText());
        }
        return result;
    }
}
