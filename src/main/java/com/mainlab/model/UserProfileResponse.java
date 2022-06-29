package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
public class UserProfileResponse implements Serializable {
    private static final long serialVersionUID = -2504107323280338568L;

    @JsonUnwrapped
    private UserProfile userProfile;
    private List<UserSkill> userSkillList;
    private List<UserCareer> userCareerList;
}
