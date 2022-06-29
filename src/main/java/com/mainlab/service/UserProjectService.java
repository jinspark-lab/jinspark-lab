package com.mainlab.service;

import com.mainlab.model.UserProject;
import com.mainlab.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {

    @Autowired
    private UserProjectRepository userProjectRepository;

    public List<UserProject> getUserProjectList(String userId) {
        return userProjectRepository.selectUserProjectList(userId);
    }
}
