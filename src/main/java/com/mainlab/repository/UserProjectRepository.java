package com.mainlab.repository;

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

    public void upsertUserProjectList(List<UserProject> userProjectList) {
        userProjectList.forEach(userProject -> {
            batchSqlSessionTemplate.insert(getMappedSql("upsertUserProject"), userProject);
        });
        batchSqlSessionTemplate.flushStatements();
    }

    public void deleteUserProjectList(List<UserProject> userProjectList) {
        userProjectList.forEach(userProject -> {
            batchSqlSessionTemplate.delete(getMappedSql("deleteUserProject"), userProject);
        });
        batchSqlSessionTemplate.flushStatements();
    }
}
