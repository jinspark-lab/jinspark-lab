package com.mainlab.service;

import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserSkillService userSkillService;
    @Autowired
    private UserCareerService userCareerService;

    public UserProfile getUserProfile(String userId) {
        return userProfileRepository.selectUserProfile(userId);
    }

    public UserProfileResponse getCompleteUserProfile(String userId) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        Optional.of(getUserProfile(userId)).ifPresent(userProfile -> {
            userProfileResponse.setUserProfile(userProfile);
            userProfileResponse.setUserSkillList(userSkillService.getUserSkillList(userId));
            userProfileResponse.setUserCareerList(userCareerService.getCompleteUserCareerList(userId));
        });
        return userProfileResponse;
    }
}
