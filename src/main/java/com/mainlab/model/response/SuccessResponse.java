package com.mainlab.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SuccessResponse {
    private int code;

    public SuccessResponse() {
        this.code = 200;
    }
}
