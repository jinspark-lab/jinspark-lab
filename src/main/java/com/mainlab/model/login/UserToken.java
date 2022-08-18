package com.mainlab.model.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserToken implements Serializable {
    private static final long serialVersionUID = -2640446403068668993L;

    private String accessToken;
    private String refreshToken;
}
