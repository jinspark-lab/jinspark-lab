package com.mainlab.service;

import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.content.ContentHashable;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ContentService {

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserAppService userAppService;

    public String linkProfileContent() {
        UserProfileResponse userProfileResponse = userProfileService.getCompleteUserProfile();
        String contentId = hashContent(userProfileResponse.getUserProfile());

        return contentId;
    }

    public String linkUserAppContent(String appId) {
        String contentId = hashContent(userAppService.getUserAppDetail(appId));

        return contentId;
    }

    private String hashContent(ContentHashable contentHashable) {
        Base62 base62 = Base62.createInstance();
        return new String(base62.encode(contentHashable.getContentKey().getBytes(StandardCharsets.UTF_8)));
    }
}
