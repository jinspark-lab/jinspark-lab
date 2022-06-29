package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Content implements Serializable {
    private static final long serialVersionUID = 7186794971784384750L;
    private int contentId;
    private ContentType contentType;
}
