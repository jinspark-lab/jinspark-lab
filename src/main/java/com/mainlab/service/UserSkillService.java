package com.mainlab.service;

import com.mainlab.model.UserSkill;
import com.mainlab.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    public List<UserSkill> getUserSkillList(String userId) {
        return userSkillRepository.selectUserSkillList(userId);
    }

    public void processUpdateUserSkillList(String userId, List<UserSkill> newUserSkillList) {
        Map<Integer, UserSkill> newUserSkillMap = newUserSkillList.stream().collect(Collectors.toMap(UserSkill::getSkillId, userSkill -> userSkill));

        List<UserSkill> oldUserSkillList = getUserSkillList(userId);
        List<UserSkill> deletedUserSkillList = oldUserSkillList.stream()
                .filter(userSkill -> !newUserSkillMap.containsKey(userSkill.getSkillId())).collect(Collectors.toList());

        deleteUserSkillList(deletedUserSkillList);
        upsertUserSkillList(newUserSkillList);
    }

    private void upsertUserSkillList(List<UserSkill> userSkillList) {
        userSkillRepository.upsertUserSkillList(userSkillList);
    }

    private void deleteUserSkillList(List<UserSkill> userSkillList) {
        userSkillRepository.deleteUserSkillList(userSkillList);
    }
}
