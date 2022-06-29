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
}
