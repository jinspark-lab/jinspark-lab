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
public class UserSkill implements Serializable {
    private static final long serialVersionUID = 4825842813676618484L;

    private int skillId;
    @JsonIgnore
    private String userId;
    private String skillName;
    private int experience;
    private SkillProficiency proficiency;
}
