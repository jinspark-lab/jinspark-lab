package com.mainlab.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FailureResponse {
    private int code;
    private String message;
    private long timestamp;
}
