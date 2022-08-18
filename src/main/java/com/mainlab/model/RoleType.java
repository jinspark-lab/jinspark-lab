package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    HOST("HOST"),
    GUEST("GUEST"),
    ;

    String value;

    public String toString() {
        return value;
    }

    public SimpleGrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(value);
    }
}
