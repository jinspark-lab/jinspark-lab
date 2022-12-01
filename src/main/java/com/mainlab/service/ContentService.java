package com.mainlab.service;

import com.google.common.collect.Lists;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserProfile;
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
    @Autowired
    private OperationService operationService;

    public UserSharablesResponse listUserSharables() {
        UserProfile userProfile = userProfileService.getCompleteUserProfile().getUserProfile();
        List<UserApp> userAppList = userAppService.getUserAppDetailList();

        String userId = userProfile.getUserId();
        Map<String, SharableContent> sharableContentMap = contentLinkRepository.selectSharableContentList(userId)
                .stream().collect(Collectors.toMap(SharableContent::getContentId, sharableContent -> sharableContent));

        UserSharablesResponse userSharablesResponse = new UserSharablesResponse();
        UserProfileSharable userProfileSharable =
                new UserProfileSharable(userId, userProfile.getContentType(), userProfile.getContentId(), userProfile.getContentLink(),
                        sharableContentMap.containsKey(userProfile.getContentId()) && sharableContentMap.get(userProfile.getContentId()).isShared());
        userSharablesResponse.setUserProfileSharable(userProfileSharable);

        userAppList.forEach(userApp -> userSharablesResponse
                .addUserApp(new UserAppSharable(userId, userApp.getAppId(), userApp.getContentType(), userApp.getContentId(), userApp.getContentLink(),
                        sharableContentMap.containsKey(userApp.getContentId()) && sharableContentMap.get(userApp.getContentId()).isShared())));

        return userSharablesResponse;
    }

    public void updateContentSharable(UserSharablesRequest userSharablesRequest) {
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);
        List<OperationUnit> operationUnitList = Lists.newLinkedList();

        UserProfileSharable userProfileSharable = userSharablesRequest.getUserProfileSharable();
        operationUnitList.add(() -> updateContentLink(queryUserId, userProfileSharable.getContentId(), ContentType.PROFILE, userProfileSharable.isShared()));
        userSharablesRequest.getUserAppSharableList().forEach(userAppSharable ->
                operationUnitList.add(() -> updateContentLink(queryUserId, userAppSharable.getContentId(), ContentType.USER_APP, userAppSharable.isShared())));

        operationService.operate(queryUserId, operationUnitList);
    }

    public void updateContentLink(String userId, String contentId, ContentType contentType, boolean shared) {
        SharableContent sharableContent = new SharableContent();
        sharableContent.setContentId(contentId);
        sharableContent.setUserId(userId);
        sharableContent.setContentType(contentType);
        sharableContent.setShared(shared);

        sharableRepository.insertSharableContent(sharableContent);

        //TODO: Insert into RDB After
        contentLinkRepository.upsertSharableContent(userId, contentId, contentType, shared);
    }
}
