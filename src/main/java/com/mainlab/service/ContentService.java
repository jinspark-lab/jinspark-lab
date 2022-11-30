package com.mainlab.service;

import com.mainlab.common.OperationType;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.content.*;
import com.mainlab.repository.ContentLinkRepository;
import com.mainlab.repository.SharableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private ContentLinkRepository contentLinkRepository;
    @Autowired
    private SharableRepository sharableRepository;

    public UserSharablesResponse listUserSharables() {
        UserProfile userProfile = userProfileService.getCompleteUserProfile().getUserProfile();
        List<UserApp> userAppList = userAppService.getUserAppDetailList();

        String userId = userProfile.getUserId();
        Map<String, SharableContent> sharableContentMap = contentLinkRepository.selectSharableContentList(userId)
                .stream().collect(Collectors.toMap(SharableContent::getContentId, sharableContent -> sharableContent));

        UserSharablesResponse userSharablesResponse = new UserSharablesResponse();
        UserProfileSharable userProfileSharable = new UserProfileSharable(userProfile, sharableContentMap.containsKey(userProfile.getContentId()));
        userSharablesResponse.setUserProfileSharable(userProfileSharable);

        userAppList.forEach(userApp -> userSharablesResponse
                .addUserApp(new UserAppSharable(userApp, sharableContentMap.containsKey(userApp.getContentId()))));

        return userSharablesResponse;
    }

    public String linkProfileContent() {
        UserProfileResponse userProfileResponse = userProfileService.getCompleteUserProfile();
        String contentId = userProfileResponse.getUserProfile().getContentId();

        addContentLink(contentId, ContentType.PROFILE);

        return contentId;
    }

    public String linkUserAppContent(String appId) {
        String contentId = userAppService.getUserAppDetail(appId).getContentId();

        return contentId;
    }

    public void addContentLink(String contentId, ContentType contentType) {
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);

        SharableContent sharableContent = new SharableContent();
        sharableContent.setContentId(contentId);
        sharableContent.setUserId(queryUserId);
        sharableContent.setContentType(contentType);

        sharableRepository.insertSharableContent(sharableContent);

        //TODO: Insert into RDB After
    }
}
