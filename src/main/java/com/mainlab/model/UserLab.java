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
public class UserLab implements Serializable {
    private static final long serialVersionUID = -8756296252579326328L;

    @JsonIgnore
    private String userId;
    private int labId;
    private String labTitle;
    private String pictureUrl;
    private String description;
    private String linkUrl;
}
