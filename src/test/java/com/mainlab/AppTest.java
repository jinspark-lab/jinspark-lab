package com.mainlab;

import com.google.common.collect.Lists;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserAppRequest;
import com.mainlab.model.UserAppShortcut;
import com.mainlab.model.UserProfileResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class AppTest extends BaseAppTest {

    @Test
    public void testApp() {
        UserProfileResponse userProfileResponse = userProfileService.getTestUserProfile();
        assertEquals("jinsangp@gmail.com", userProfileResponse.getUserProfile().getUserId());
    }

    @Test
    public void testCache() {
        UserProfileResponse userProfileResponse = userProfileService.getTestUserProfile("test");
        Optional<UserProfileResponse> cachedResponse = Optional.ofNullable(cacheManager.getCache("testUserProfile"))
                .map(cache -> cache.get("test", UserProfileResponse.class));
        assertTrue(cachedResponse.isPresent());
        assertEquals(userProfileResponse.getUserProfile().getUserId(), cachedResponse.get().getUserProfile().getUserId());
    }

    @Test
    public void testStorage() {
        Optional<Bucket> staticBucket = storageService.getS3Client().listBuckets().buckets().stream().filter(bucket -> bucket.name().equals("jinspark-lab-static-resource-bucket")).findAny();
        assertTrue(staticBucket.isPresent());
    }

    @Test
    public void testUserApp() {
        String uid = "tester";
        String appId = "TestUserApp";
        String repoLink = "Hello.git";
        String thumbnailUrl = "test.png";

        UserAppRequest userAppRequest = new UserAppRequest();
        userAppRequest.setUserApp(new UserApp());
        userAppRequest.setThumbnailUrl(thumbnailUrl);

        UserApp userApp = new UserApp();
        userApp.setUserId(uid);
        userApp.setAppId(appId);
        userApp.setRepoLink(repoLink);
        userApp.setIntroText("");
        userApp.setAppLink("");
        userApp.setAppPicture("");
        userApp.setDescription("");
        userApp.setArchitectureUrl("");

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppRepository.insertUserApp(uid, userApp));
        operationUnitList.add(() ->
                userAppShortcutRepository.insertUserAppShortcut(uid,
                        new UserAppShortcut(uid, userApp.getAppId(), userAppRequest.getThumbnailUrl())));

        operationService.operate(uid, operationUnitList);

        UserApp userAppFromDB = userAppRepository.selectUserApp(uid, appId);
        UserAppShortcut userAppShortcutFromDB = userAppShortcutRepository.selectUserAppShortcutList(uid).stream().filter(userAppShortcut -> userAppShortcut.getAppId().equals(appId)).findAny().get();
        assertEquals(userAppFromDB.getRepoLink(), repoLink);
        assertEquals(userAppShortcutFromDB.getThumbnailUrl(), thumbnailUrl);
    }

    @After
    public void clearUserAppTest() {
        String uid = "tester";
        String appId = "TestUserApp";

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppShortcutRepository.deleteUserAppShortcut(uid, appId));
        operationUnitList.add(() -> userAppRepository.deleteUserApp(uid, appId));
        operationService.operate(uid, operationUnitList);
    }
}
