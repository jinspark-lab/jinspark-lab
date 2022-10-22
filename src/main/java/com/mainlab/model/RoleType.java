package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN", 0),
    HOST("HOST", 1),
    GUEST("GUEST", 10),
    ;

    String value;

    int level;

    public String toString() {
        return value;
    }

    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(value);
    }
}
