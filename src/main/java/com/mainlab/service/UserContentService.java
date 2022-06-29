package com.mainlab.service;

import com.mainlab.model.UserContent;
import com.mainlab.repository.UserContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContentService {

    @Autowired
    private UserContentRepository userContentRepository;

    public UserContent getUserContent(String userId) {
        return userContentRepository.selectUserContent(userId);
    }
}
