package com.mainlab.repository;

import com.mainlab.model.UserSkill;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSkillRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserSkillSql";
    }

    public List<UserSkill> selectUserSkillList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserSkillList"), userId);
    }

    public void upsertUserSkillList(List<UserSkill> userSkillList) {
        if (!userSkillList.isEmpty()) {
            userSkillList.forEach(userSkill -> {
                sqlSessionTemplate.insert(getMappedSql("upsertUserSkill"), userSkill);
            });
            sqlSessionTemplate.flushStatements();
        }
    }

    public void deleteUserSkillList(List<UserSkill> userSkillList) {
        if (!userSkillList.isEmpty()) {
            userSkillList.forEach(userSkill -> {
                sqlSessionTemplate.delete(getMappedSql("deleteUserSkill"), userSkill);
            });
            sqlSessionTemplate.flushStatements();
        }
    }
}
