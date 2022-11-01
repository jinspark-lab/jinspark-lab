package com.mainlab.model;

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
public class UserAppResponse implements Serializable {
    private static final long serialVersionUID = -80925362380920556L;

    @JsonUnwrapped
    private UserApp userApp;
    private String thumbnailUrl;
}
