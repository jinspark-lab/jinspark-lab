package com.mainlab.model.blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlogListResponse implements Serializable {
    private static final long serialVersionUID = -517368772412634183L;

    private List<UserBlog> userBlogList;
}
