package com.mainlab.service;

import com.google.common.collect.Lists;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.blog.UserBlog;
import com.mainlab.model.content.*;
import com.mainlab.repository.ContentLinkRepository;
import com.mainlab.repository.dynamo.BlogRepository;
import com.mainlab.repository.dynamo.ProfileRepository;
import com.mainlab.repository.dynamo.SharableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private UserBlogService userBlogService;
    @Autowired
    private ContentLinkRepository contentLinkRepository;
    @Autowired
    private SharableRepository sharableRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private OperationService operationService;

    public UserSharablesResponse listUserSharables() {
        UserProfile userProfile = userProfileService.getCompleteUserProfile().getUserProfile();
        List<UserApp> userAppList = userAppService.getUserAppDetailList();
        List<UserBlog> userBlogList = userBlogService.getUserBlogList();

        String userId = userProfile.getUserId();
        Map<String, SharableContent> sharableContentMap = contentLinkRepository.selectSharableContentList(userId)
                .stream().collect(Collectors.toMap(SharableContent::getContentId, sharableContent -> sharableContent));

        UserSharablesResponse userSharablesResponse = new UserSharablesResponse();
        UserProfileSharable userProfileSharable = UserProfileSharable.builder()
                .userId(userId)
                .contentType(userProfile.getContentType())
                .contentId(userProfile.getContentId())
                .contentLink(userProfile.getContentLink())
                .shared(sharableContentMap.containsKey(userProfile.getContentId()) && sharableContentMap.get(userProfile.getContentId()).isShared())
                .build();
        userSharablesResponse.setUserProfileSharable(userProfileSharable);

        userAppList.forEach(userApp -> userSharablesResponse
                .addUserApp(UserAppSharable.builder()
                        .userId(userId)
                        .appId(userApp.getAppId())
                        .contentType(userApp.getContentType())
                        .contentId(userApp.getContentId())
                        .contentLink(userApp.getContentLink())
                        .shared(sharableContentMap.containsKey(userApp.getContentId()) && sharableContentMap.get(userApp.getContentId()).isShared())
                        .build()));

        userBlogList.forEach(userBlog -> userSharablesResponse
                        .addUserBlog(UserBlogSharable.builder()
                                .userId(userId)
                                .blogId(userBlog.getBlogId())
                                .contentType(userBlog.getContentType())
                                .contentId(userBlog.getContentId())
                                .contentLink(userBlog.getContentLink())
                                .shared(sharableContentMap.containsKey(userBlog.getContentId()) && sharableContentMap.get(userBlog.getContentId()).isShared())
                                .build()));

        return userSharablesResponse;
    }

    public void updateContentSharable(UserSharablesRequest userSharablesRequest) {
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);
        List<OperationUnit> operationUnitList = Lists.newLinkedList();

        UserProfileSharable userProfileSharable = userSharablesRequest.getUserProfileSharable();

        // User Profile Transaction
        operationUnitList.add(new OperationUnit() {
            @Override
            public void operate() {
                putContentLink(queryUserId, userProfileSharable.getContentId(), ContentType.PROFILE, userProfileSharable.isShared());
                putProfileContent(queryUserId, userProfileSharable.getContentId());
            }

            @Override
            public boolean isCallbackPossible() {
                return true;
            }

            @Override
            public void callback() {
                contentLinkRepository.upsertSharableContent(queryUserId, userProfileSharable.getContentId(), ContentType.PROFILE, userProfileSharable.isShared());
            }
        });

        Optional.ofNullable(userSharablesRequest.getUserAppSharableList()).ifPresent(userAppSharables -> {
            // User App Transaction
            userAppSharables.forEach(userAppSharable ->
                    operationUnitList.add(new OperationUnit() {
                        @Override
                        public void operate() {
                            putContentLink(queryUserId, userAppSharable.getContentId(), ContentType.USER_APP, userAppSharable.isShared());
                        }

                        @Override
                        public boolean isCallbackPossible() {
                            return true;
                        }

                        @Override
                        public void callback() {
                            contentLinkRepository.upsertSharableContent(queryUserId, userAppSharable.getContentId(), ContentType.USER_APP, userAppSharable.isShared());
                        }
                    }));
        });

        Optional.ofNullable(userSharablesRequest.getUserBlogSharableList()).ifPresent(userBlogSharables -> {
            // User Blog Transaction
            userBlogSharables.forEach(userBlogSharable ->
                    operationUnitList.add(new OperationUnit() {
                        @Override
                        public void operate() {
                            putContentLink(queryUserId, userBlogSharable.getContentId(), ContentType.BLOG, userBlogSharable.isShared());
                            putBlogContent(queryUserId, userBlogSharable.getContentId(), userBlogSharable.getBlogId());
                        }

                        public boolean isCallbackPossible() {
                            return true;
                        }

                        public void callback() {
                            contentLinkRepository.upsertSharableContent(queryUserId, userBlogSharable.getContentId(), ContentType.BLOG, userBlogSharable.isShared());
                        }
                    }));
        });

        operationService.operate(queryUserId, operationUnitList);
    }

    private void putContentLink(String userId, String contentId, ContentType contentType, boolean shared) {
        sharableRepository.insertSharableContent(SharableContent.builder()
                        .contentId(contentId)
                        .userId(userId)
                        .contentType(contentType)
                        .shared(shared)
                .build());
    }

    private void putProfileContent(String userId, String contentId) {
        UserProfileResponse userProfileResponse = userProfileService.getCompleteUserProfile(userId);
        profileRepository.insertProfileContent(contentId, userProfileResponse);
    }

    private void putBlogContent(String userId, String contentId, int blogId) {
        UserBlog userBlog = userBlogService.getUserBlog(userId, blogId);
        blogRepository.insertBlogContent(contentId, userBlog.getContent());
    }
}
