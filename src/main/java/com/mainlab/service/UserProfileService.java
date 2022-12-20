package com.mainlab.service;

import com.mainlab.common.ObjectConvertService;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserProfile;
import com.mainlab.model.UserProfileRequest;
import com.mainlab.model.UserProfileResponse;
import com.mainlab.model.UserProject;
import com.mainlab.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserSkillService userSkillService;
    @Autowired
    private UserCareerService userCareerService;
    @Autowired
    private UserProjectService userProjectService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private AppLogService appLogService;
    @Autowired
    private ObjectConvertService objectConvertService;

    private UserProfile getUserProfile(String userId) {
        return userProfileRepository.selectUserProfile(userId);
    }

    private void updateUserProfile(String userId, UserProfile userProfile) {
        userProfile.setUserId(userId);
        userProfileRepository.updateUserProfile(userId, userProfile);
    }

    public UserProfileResponse getTestUserProfile() {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        String userId = "jinsangp@gmail.com";
        Optional.of(getUserProfile(userId)).ifPresent(userProfile -> {
            userProfileResponse.setUserProfile(userProfile);
            userProfileResponse.setUserSkillList(userSkillService.getUserSkillList(userId));
            userProfileResponse.setUserCareerList(userCareerService.getCompleteUserCareerList(userId));
        });
        return userProfileResponse;
    }

    @Cacheable(value = "testUserProfile", key = "#key")
    public UserProfileResponse getTestUserProfile(String key) {
        return getTestUserProfile();
    }

    public UserProfileResponse getCompleteUserProfile() {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        String queryUserId = userService.getOperationUserId(OperationType.READ);

        Optional.of(getUserProfile(queryUserId)).ifPresent(userProfile -> {
            userProfileResponse.setUserProfile(userProfile);
            userProfileResponse.setUserSkillList(userSkillService.getUserSkillList(queryUserId));
            userProfileResponse.setUserCareerList(userCareerService.getCompleteUserCareerList(queryUserId));
        });
        return userProfileResponse;
    }

    public void getProcessedUserProfile(UserProfileRequest userProfileRequest) {
        List<OperationUnit> operationUnitList = new LinkedList<>();
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);

        // Flatize Transactional operations in operationUnitList
        operationUnitList.add(() -> updateUserProfile(queryUserId, userProfileRequest.getUserProfile()));
        operationUnitList.addAll(userSkillService.processUpdateUserSkillList(queryUserId, userProfileRequest.getUserSkillList()));
        operationUnitList.addAll(userCareerService.processUpdateUserCareerList(queryUserId, userProfileRequest.getUserCareerList()));

        List<UserProject> userProjectList = userProfileRequest.getUserCareerList().stream()
                .flatMap(userCareer -> userCareer.getUserProjectList().stream()).collect(Collectors.toList());
        operationUnitList.addAll(userProjectService.processUpdateUserProjectList(queryUserId, userProjectList));

        operationService.operate(queryUserId, operationUnitList);
        appLogService.info("Update User Profile : " + objectConvertService.objToString(userProfileRequest));
    }
}
