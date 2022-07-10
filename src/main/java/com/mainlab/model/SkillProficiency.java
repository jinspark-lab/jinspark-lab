package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SkillProficiency {
    BEGINNER(1),
    GOOD(2),
    ADVANCED(3),
    PROFESSIONAL(4),
    EXPERT(5);

    private int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static SkillProficiency from(Integer value) {
        return Arrays.stream(SkillProficiency.values()).filter(skillProficiency -> skillProficiency.getValue() == value).findFirst().get();
    }
}
