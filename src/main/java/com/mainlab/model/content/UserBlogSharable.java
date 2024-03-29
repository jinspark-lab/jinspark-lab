package com.mainlab.model.content;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBlogSharable implements Serializable {
    private static final long serialVersionUID = 7544257353966960466L;

    private String userId;
    private int blogId;
    private ContentType contentType;
    private String contentId;
    private String contentLink;
    private boolean shared;
}
