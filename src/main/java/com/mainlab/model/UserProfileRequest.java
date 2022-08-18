package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequest implements Serializable {
    private static final long serialVersionUID = 636365245445632087L;

    private String userId;
    private UserProfile userProfile;
    private List<UserSkill> userSkillList;
    private List<UserCareer> userCareerList;
}
