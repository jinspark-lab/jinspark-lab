package com.mainlab.model.blog;

import com.mainlab.model.content.ContentHashable;
import com.mainlab.model.content.ContentType;
import com.mainlab.model.content.SharableContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlog implements Serializable, ContentHashable {
    private static final long serialVersionUID = 51396275786510206L;

    private String userId;
    private int blogId;
    private String title;
    private String content;

    @Override
    public String getContentKey() {
        return userId + "_" + blogId;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.BLOG;
    }
}
