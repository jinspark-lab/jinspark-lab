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
public class UserAppRequest implements Serializable {
    private static final long serialVersionUID = -3927139362050414193L;

    private UserApp userApp;
    private String thumbnailUrl;
}
