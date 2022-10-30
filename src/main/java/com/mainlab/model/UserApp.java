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
public class UserApp implements Serializable {
    private static final long serialVersionUID = -1245371694984418669L;

    private String userId;
    private String appId;
    private String repoLink;
    private String introText;
    private String appLink;
    private String architectureLink;
    private String description;
}
