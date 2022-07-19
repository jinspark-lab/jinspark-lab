package com.mainlab.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class DateTimeDeserializer extends JsonDeserializer<DateTime> {

    @Setter
    private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            return formatter.parseDateTime(jsonParser.getText()).withZone(DateTimeZone.UTC);
        } else if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            return new DateTime(Long.valueOf(jsonParser.getText())).withZone(DateTimeZone.UTC);
        }
        return (DateTime) deserializationContext.handleUnexpectedToken(DateTime.class, jsonParser);
    }
}
