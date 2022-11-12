package com.mainlab.service;

import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserCareer;
import com.mainlab.model.UserProject;
import com.mainlab.repository.UserCareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserCareerService {
    @Autowired
    private UserCareerRepository userCareerRepository;
    @Autowired
    private UserProjectService userProjectService;

    public List<UserCareer> getCompleteUserCareerList(String userId) {
        List<UserCareer> userCareerList = getUserCareerList(userId);
        Map<Integer, List<UserProject>> userProjectMap = userProjectService.getUserProjectList(userId).stream().collect(Collectors.groupingBy(UserProject::getCareerId));
        userCareerList.forEach(userCareer -> userCareer.setUserProjectList(userProjectMap.get(userCareer.getCareerId())));
        return userCareerList;
    }

    public List<OperationUnit> processUpdateUserCareerList(String userId, List<UserCareer> userCareerList) {
        List<OperationUnit> operationUnitList = new LinkedList<>();
        Map<Integer, UserCareer> newUserCareerMap = userCareerList.stream().collect(Collectors.toMap(UserCareer::getCareerId, userCareer -> userCareer));

        List<UserCareer> oldUserCareerList = getUserCareerList(userId);
        List<UserCareer> deletedUserCareerList = oldUserCareerList.stream()
                .filter(userCareer -> !newUserCareerMap.containsKey(userCareer.getCareerId())).collect(Collectors.toList());

        operationUnitList.add(() -> deleteUserCareerList(userId, deletedUserCareerList));
        operationUnitList.add(() -> upsertUserCareerList(userId, userCareerList));
        return operationUnitList;
    }

    private List<UserCareer> getUserCareerList(String userId) {
        return userCareerRepository.selectUserCareerList(userId);
    }

    private void upsertUserCareerList(String userId, List<UserCareer> userCareerList) {
        userCareerRepository.upsertUserCareerList(userId, userCareerList);
    }

    private void deleteUserCareerList(String userId, List<UserCareer> userCareerList) {
        userCareerRepository.deleteUserCareerList(userId, userCareerList);
    }
}
