package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mainlab.model.content.ContentHashable;
import com.mainlab.model.content.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Serializable, ContentHashable {
    private static final long serialVersionUID = 247560237355517096L;

    private String userId;
    private String name;
    private String title;
    private String pictureUrl;
    private String description;
    private String linkedinUrl;
    private String contactEmail;

    @JsonIgnore
    @Override
    public String getContentKey() {
        return userId;
    }

    @JsonIgnore
    @Override
    public ContentType getContentType() {
        return ContentType.PROFILE;
    }
}
