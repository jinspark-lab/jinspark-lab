package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 247560237355517096L;

    private String userId;
    private String name;
    private String title;
    private String pictureUrl;
    private String description;
    private String linkedinUrl;
    private String contactEmail;
}
