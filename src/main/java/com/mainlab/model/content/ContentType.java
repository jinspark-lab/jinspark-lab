package com.mainlab.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ContentType {
    PROFILE("https://es6b47ymad.execute-api.us-east-1.amazonaws.com/prod/profile"),
    USER_APP("https://es6b47ymad.execute-api.us-east-1.amazonaws.com/prod/app"),
    ;

    private String contentUrl;
}
