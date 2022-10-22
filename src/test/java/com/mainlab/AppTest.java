package com.mainlab;

import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.service.UserProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class AppTest {

    @Autowired
    private UserProfileService userProfileService;

    @Test
    public void testApp() {
        UserProfileResponse userProfileResponse = userProfileService.getTestUserProfile();
        assertEquals("jinsangp@gmail.com", userProfileResponse.getUserProfile().getUserId());
    }
}
