package com.mainlab.model.login;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 3112758934467167169L;

    @JsonUnwrapped
    private UserToken userToken;
}
