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
public class UserApp implements Serializable, ContentHashable {
    private static final long serialVersionUID = -1245371694984418669L;

    private String userId;
    private String appId;
    private String repoLink;
    private String introText;
    private String appLink;
    private String appPicture;
    private String architectureUrl;
    private String description;

    @JsonIgnore
    @Override
    public String getContentKey() {
        return userId + '_' + appId;
    }

    @JsonIgnore
    @Override
    public ContentType getContentType() {
        return ContentType.USER_APP;
    }
}
