package com.mainlab.repository;

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

    public void upsertUserCareerList(List<UserCareer> userCareerList) {
        if (!userCareerList.isEmpty()) {
            userCareerList.forEach(userCareer -> {
                sqlSessionTemplate.insert(getMappedSql("upsertUserCareer"), userCareer);
            });
            sqlSessionTemplate.flushStatements();
        }
    }

    public void deleteUserCareerList(List<UserCareer> userCareerList) {
        if (!userCareerList.isEmpty()) {
            userCareerList.forEach(userCareer ->
                    sqlSessionTemplate.delete(getMappedSql("deleteUserCareer"), userCareer));
            sqlSessionTemplate.flushStatements();
        }
    }
}
