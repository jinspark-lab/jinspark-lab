package com.mainlab.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Objects;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

    @Setter
    private DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (dateTime == null || formatter == null || jsonGenerator == null) {
            System.out.println(dateTime);
        }
        Objects.requireNonNull(jsonGenerator).writeString(formatter.print(dateTime));
    }

    @Override
    public Class<DateTime> handledType() {
        return DateTime.class;
    }
}
