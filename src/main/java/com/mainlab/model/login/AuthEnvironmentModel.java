package com.mainlab.model.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthEnvironmentModel {
    private String issuer;
    private String secret;
}
