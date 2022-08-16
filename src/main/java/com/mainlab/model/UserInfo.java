package com.mainlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 3792167029375600814L;

    private String userId;              //email
    private String refreshToken;
    @JsonIgnore
    private int shardId;
    private DateTime lastLogin;

    private List<UserRole> userRoleList;

    public List<RoleType> getRoleTypeList() {
        return userRoleList.stream().map(UserRole::getRoleType).collect(Collectors.toList());
    }

    public UserInfo(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.shardId = 1;
        this.lastLogin = DateTime.now();
    }
}
