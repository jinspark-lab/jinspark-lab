package com.mainlab.model.content;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mainlab.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAppSharable implements Serializable {
    private static final long serialVersionUID = -6480006389423113883L;

    @JsonIgnore
    private UserApp userApp;
    @JsonIgnore
    private boolean shared;

    public String getUserId() {
        return userApp.getUserId();
    }

    public String getAppId() {
        return userApp.getAppId();
    }

    public ContentType getContentType() {
        return userApp.getContentType();
    }

    public String getContentId() {
        return userApp.getContentId();
    }

    public String getContentLink() {
        return userApp.getContentLink();
    }

    public boolean isShared() {
        return shared;
    }
}
