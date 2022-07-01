package com.mainlab.service;

import com.mainlab.model.UserLab;
import com.mainlab.model.UserLabResponse;
import com.mainlab.repository.UserLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLabService {
    @Autowired
    private UserLabRepository userLabRepository;

    private List<UserLab> getUserLabList(String userId) {
        return userLabRepository.selectUserLabList(userId);
    }

    public UserLabResponse getUserLabResponse(String userId) {
        UserLabResponse userLabResponse = new UserLabResponse();
        userLabResponse.setUserLabList(getUserLabList(userId));
        return userLabResponse;
    }
}
