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
public class UserAppShortcut implements Serializable {
    private static final long serialVersionUID = -8453263288436428949L;

    private String userId;
    private String appId;
    private String thumbnailUrl;
}
