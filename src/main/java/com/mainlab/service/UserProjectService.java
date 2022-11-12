package com.mainlab.service;

import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserProject;
import com.mainlab.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserProjectService {

    @Autowired
    private UserProjectRepository userProjectRepository;

    public List<UserProject> getUserProjectList(String userId) {
        return userProjectRepository.selectUserProjectList(userId);
    }

    public List<OperationUnit> processUpdateUserProjectList(String userId, List<UserProject> userProjectList) {
        List<OperationUnit> operationUnitList = new LinkedList<>();

        Map<String, UserProject> newUniqueUserProjectMap = userProjectList.stream()
                .collect(Collectors.toMap(UserProject::getUniqueProjectId, userProject -> userProject));
        List<UserProject> oldUserProjectList = getUserProjectList(userId);
        List<UserProject> deletedUserProjectList = oldUserProjectList.stream()
                .filter(userProject -> !newUniqueUserProjectMap.containsKey(userProject.getUniqueProjectId()))
                .collect(Collectors.toList());

        operationUnitList.add(() -> deleteUserProjectList(userId, deletedUserProjectList));
        operationUnitList.add(() -> upsertUserProjectList(userId, userProjectList));
        return operationUnitList;
    }

    private void upsertUserProjectList(String userId, List<UserProject> userProjectList) {
        userProjectRepository.upsertUserProjectList(userId, userProjectList);
    }

    private void deleteUserProjectList(String userId, List<UserProject> userProjectList) {
        userProjectRepository.deleteUserProjectList(userId, userProjectList);
    }
}
