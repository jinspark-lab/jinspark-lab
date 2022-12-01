package com.mainlab.model.content;

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
public class UserSharablesRequest implements Serializable {
    private static final long serialVersionUID = 7841079265231703909L;

    private UserProfileSharable userProfileSharable;
    private List<UserAppSharable> userAppSharableList;
}
