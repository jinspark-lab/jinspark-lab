package com.mainlab.service;

import com.mainlab.model.UserCareer;
import com.mainlab.model.UserProject;
import com.mainlab.repository.UserCareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<UserCareer> getUserCareerList(String userId) {
        return userCareerRepository.selectUserCareerList(userId);
    }
}
