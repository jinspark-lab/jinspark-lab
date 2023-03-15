package com.mainlab.model.content;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSharable implements Serializable {
    private static final long serialVersionUID = 7147783557842246104L;

    private String userId;
    private ContentType contentType;
    private String contentId;
    private String contentLink;
    private boolean shared;
}
