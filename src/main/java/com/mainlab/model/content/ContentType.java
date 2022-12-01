package com.mainlab.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ContentType {
    PROFILE("http://www.profileservice.url"),
    USER_APP("http://www.userappservice.url"),
    ;

    private String contentUrl;



}
