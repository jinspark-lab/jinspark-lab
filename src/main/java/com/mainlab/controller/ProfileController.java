package com.mainlab.controller;

import com.mainlab.model.UserProfileRequest;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.response.SuccessResponse;
import com.mainlab.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/profile")
public class ProfileController extends BaseController {

    @Autowired
    private UserProfileService userProfileService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserProfileResponse getUserProfile() {
        return userProfileService.getCompleteUserProfile();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public SuccessResponse updateAndGetUserProfile(@RequestBody UserProfileRequest userProfileRequest) {
        userProfileService.updateCompleteUserProfile(userProfileRequest);
        return new SuccessResponse();
    }
}
