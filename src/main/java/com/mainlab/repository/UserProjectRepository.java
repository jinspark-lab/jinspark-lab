package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.UserProject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProjectRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserProjectSql";
    }

    public List<UserProject> selectUserProjectList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserProjectList"), userId);
    }

    public void upsertUserProjectList(String userId, List<UserProject> userProjectList) {
        if (!userProjectList.isEmpty()) {
            userProjectList.forEach(userProject -> {
                sqlSessionTemplate.insert(getMappedSql("upsertUserProject"), ImmutableMap.of("userId", userId, "userProject", userProject));
            });
            sqlSessionTemplate.flushStatements();
        }
    }

    public void deleteUserProjectList(String userId, List<UserProject> userProjectList) {
        if (!userProjectList.isEmpty()) {
            userProjectList.forEach(userProject -> {
                sqlSessionTemplate.delete(getMappedSql("deleteUserProject"), ImmutableMap.of("userId", userId, "userProject", userProject));
            });
            sqlSessionTemplate.flushStatements();
        }
    }
}
