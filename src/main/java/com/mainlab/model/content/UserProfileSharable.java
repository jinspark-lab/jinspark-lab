package com.mainlab.model.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mainlab.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSharable implements Serializable {
    private static final long serialVersionUID = 7147783557842246104L;

    @JsonIgnore
    private UserProfile userProfile;
    @JsonIgnore
    private boolean shared;

    public String getUserId() {
        return userProfile.getUserId();
    }

    public ContentType getContentType() {
        return userProfile.getContentType();
    }

    public String getContentId() {
        return userProfile.getContentId();
    }

    public String getContentLink() {
        return userProfile.getContentLink();
    }

    public boolean isShared() {
        return shared;
    }
}
