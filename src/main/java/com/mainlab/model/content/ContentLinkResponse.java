package com.mainlab.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentLinkResponse implements Serializable {
    private static final long serialVersionUID = -8513927309002045393L;

    private String contentId;
}
