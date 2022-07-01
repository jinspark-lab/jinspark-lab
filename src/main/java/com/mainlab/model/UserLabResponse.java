package com.mainlab.model;

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
public class UserLabResponse implements Serializable {
    private static final long serialVersionUID = 1156650488309188054L;

    public List<UserLab> userLabList;
}
