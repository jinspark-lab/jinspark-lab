package com.mainlab.controller;

import com.mainlab.model.UserProfileResponse;
import com.mainlab.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // GET 을 쓰게되면 앞에 CF 를 두게되면 역시 캐싱하겠지.. 캐싱될 경우 Invalidation 도 어려움
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public UserProfileResponse getUserProfile(@PathVariable("userId") String userId) {
        return userProfileService.getCompleteUserProfile(userId);
    }
}
