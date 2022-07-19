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
public class UserProject implements Serializable {
    private static final long serialVersionUID = 4711464642341895795L;

    private int projectId;
    private int careerId;
    private String userId;
    private int projectOrder;
    private String description;

    @JsonIgnore
    public String getUniqueProjectId() {
        return careerId + ":" + projectId;
    }
}
