package com.mainlab.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Getter
@Builder
public class AppLogRecord {
    @JsonIgnore
    private DateTime dateTime;     //@timestamp
    private String host;
    private String userId;
    private LogLevel logLevel;
    private RecordType recordType;
    private String message;
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

    @JsonProperty("@timestamp")
    public String getTimestamp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        return dateTimeFormatter.print(dateTime);
    }
}
