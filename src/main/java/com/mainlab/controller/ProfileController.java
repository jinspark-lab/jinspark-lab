package com.mainlab.controller;

import com.mainlab.model.UserProfileRequest;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // FIXME: Fix pathvariable to requestbody
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public UserProfileResponse getUserProfile(@PathVariable("userId") String userId) {
        return userProfileService.getCompleteUserProfile(userId);
    }

    // TODO: Design Common RequestBody include user information. Using ThreadLocal
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateAndGetUserProfile(@RequestBody UserProfileRequest userProfileRequest) {
        userProfileService.getProcessedUserProfile("admin", userProfileRequest);
        return ResponseEntity.ok().build();
    }
}
