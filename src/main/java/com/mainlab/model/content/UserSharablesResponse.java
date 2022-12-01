package com.mainlab.model.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSharablesResponse implements Serializable {
    private static final long serialVersionUID = 1874982277518082822L;

    private UserProfileSharable userProfileSharable;
    private List<UserAppSharable> userAppSharableList;

    public void addUserApp(UserAppSharable userAppSharable) {
        if (Optional.ofNullable(userAppSharableList).isEmpty()) {
            userAppSharableList = new LinkedList<>();
        }
        userAppSharableList.add(userAppSharable);
    }
}
