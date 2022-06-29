package com.mainlab.service;

import com.mainlab.model.UserSkill;
import com.mainlab.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    public List<UserSkill> getUserSkillList(String userId) {
        return userSkillRepository.selectUserSkillList(userId);
    }
}
