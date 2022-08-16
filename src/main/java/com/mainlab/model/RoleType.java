package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    HOST("HOST"),
    GUEST("GUEST"),;

    String value;

    public String toString() {
        return value;
    }
}
