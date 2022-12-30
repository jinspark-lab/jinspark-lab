package com.mainlab.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ContentType {
    PROFILE("/share/profile"),
    USER_APP("/share/app"),
    BLOG("/share/blog"),
    ;

    private String contentUrl;
}
