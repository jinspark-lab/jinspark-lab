package com.mainlab.model.content;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharableContent implements Serializable {
    private static final long serialVersionUID = -6193093376753844157L;

    private String contentId;
    private String userId;
    private ContentType contentType;
    private boolean shared;
}
