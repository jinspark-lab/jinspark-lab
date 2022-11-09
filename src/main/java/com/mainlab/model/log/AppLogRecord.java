package com.mainlab.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

@Getter
@Builder
public class AppLogRecord {
    @JsonIgnore
    private DateTime dateTime;     //@timestamp
    private String userId;
    private LogLevel logLevel;
    private RecordType recordType;
    private String message;
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

    @JsonProperty("@timestamp")
    public String getTimestamp() {
        return "" + dateTime.getMillis();
    }
}
