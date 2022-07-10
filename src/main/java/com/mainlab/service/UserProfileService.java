package com.mainlab.service;

import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileRequest;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.UserProject;
import com.mainlab.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserSkillService userSkillService;
    @Autowired
    private UserCareerService userCareerService;
    @Autowired
    private UserProjectService userProjectService;

    private UserProfile getUserProfile(String userId) {
        return userProfileRepository.selectUserProfile(userId);
    }

    private void updateUserProfile(String userId, UserProfile userProfile) {
        userProfile.setUserId(userId);
        userProfileRepository.updateUserProfile(userProfile);
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

    public UserProfileResponse getProcessedUserProfile(String userId, UserProfileRequest userProfileRequest) {
        updateUserProfile(userId, userProfileRequest.getUserProfile());
        userSkillService.processUpdateUserSkillList(userId, userProfileRequest.getUserSkillList());
        userCareerService.processUpdateUserCareerList(userId, userProfileRequest.getUserCareerList());

        List<UserProject> userProjectList = userProfileRequest.getUserCareerList().stream()
                .flatMap(userCareer -> userCareer.getUserProjectList().stream()).collect(Collectors.toList());
        userProjectService.processUpdateUserProjectList(userId, userProjectList);

        return getCompleteUserProfile(userId);
    }
}
