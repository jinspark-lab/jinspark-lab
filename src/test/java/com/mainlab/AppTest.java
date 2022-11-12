package com.mainlab;

import com.mainlab.model.UserProfileResponse;
import com.mainlab.service.StorageService;
import com.mainlab.service.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class AppTest {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private StorageService storageService;

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
}
