package com.mainlab.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class BaseAppMessage implements Serializable {
    private static final long serialVersionUID = 1893160660812297775L;

    @Getter
    @Setter
    private AppMessageType appMessageType;
    @JsonIgnore
    private final DateTime timestamp;

    public BaseAppMessage(AppMessageType appMessageType) {
        this.appMessageType = appMessageType;
        this.timestamp = DateTime.now();
    }

    public String getMessageId() {
        return getTimestamp() + "_" + getMessageBody().hashCode();
    }

    public long getTimestamp() {
        return timestamp.getMillis();
    }

    public abstract Object getMessageBody();
}
