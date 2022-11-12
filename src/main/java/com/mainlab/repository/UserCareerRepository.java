package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.UserCareer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCareerRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserCareerSql";
    }

    public List<UserCareer> selectUserCareerList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserCareerList"), userId);
    }

    public void upsertUserCareerList(String userId, List<UserCareer> userCareerList) {
        if (!userCareerList.isEmpty()) {
            userCareerList.forEach(userCareer -> {
                sqlSessionTemplate.insert(getMappedSql("upsertUserCareer"), ImmutableMap.of("userId", userId, "userCareer", userCareer));
            });
            sqlSessionTemplate.flushStatements();
        }
    }

    public void deleteUserCareerList(String userId, List<UserCareer> userCareerList) {
        if (!userCareerList.isEmpty()) {
            userCareerList.forEach(userCareer ->
                    sqlSessionTemplate.delete(getMappedSql("deleteUserCareer"), ImmutableMap.of("userId", userId, "userCareer", userCareer)));
            sqlSessionTemplate.flushStatements();
        }
    }
}
