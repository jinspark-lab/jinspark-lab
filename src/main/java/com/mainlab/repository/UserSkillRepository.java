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
        userSkillList.forEach(userSkill -> {
            batchSqlSessionTemplate.insert(getMappedSql("upsertUserSkill"), userSkill);
        });
        batchSqlSessionTemplate.flushStatements();
    }

    public void deleteUserSkillList(List<UserSkill> userSkillList) {
        userSkillList.forEach(userSkill -> {
            batchSqlSessionTemplate.delete(getMappedSql("deleteUserSkill"), userSkill);
        });
        batchSqlSessionTemplate.flushStatements();
    }
}
