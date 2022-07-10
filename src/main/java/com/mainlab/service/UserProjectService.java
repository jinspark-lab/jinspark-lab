package com.mainlab.service;

import com.mainlab.model.UserProject;
import com.mainlab.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void processUpdateUserProjectList(String userId, List<UserProject> userProjectList) {
        Map<Integer, UserProject> newUserProjectProjectMap = userProjectList.stream().collect(Collectors.toMap(UserProject::getProjectId, userProject -> userProject));
        Map<Integer, UserProject> newUserProjectCareerMap = userProjectList.stream().collect(Collectors.toMap(UserProject::getCareerId, userProject -> userProject));

        List<UserProject> oldUserProjectList = getUserProjectList(userId);
        List<UserProject> deletedUserProjectList = oldUserProjectList.stream()
                .filter(userProject -> !newUserProjectProjectMap.containsKey(userProject.getProjectId())
                        && !newUserProjectCareerMap.containsKey(userProject.getCareerId()))
                .collect(Collectors.toList());

        deleteUserProjectList(deletedUserProjectList);
        upsertUserProjectList(userProjectList);
    }

    private void upsertUserProjectList(List<UserProject> userProjectList) {
        userProjectRepository.upsertUserProjectList(userProjectList);
    }

    private void deleteUserProjectList(List<UserProject> userProjectList) {
        userProjectRepository.deleteUserProjectList(userProjectList);
    }
}
