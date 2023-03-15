package com.mainlab.model.content;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAppSharable implements Serializable {
    private static final long serialVersionUID = -6480006389423113883L;

    private String userId;
    private String appId;
    private ContentType contentType;
    private String contentId;
    private String contentLink;
    private boolean shared;
}
