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
public class UserContent implements Serializable {
    private static final long serialVersionUID = -6082221921505715555L;
    private String userId;
    private int algorithmCnt;
    private int architectureCnt;
    private int projectCnt;
}
