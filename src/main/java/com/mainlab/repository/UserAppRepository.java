package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.UserApp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAppRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserAppSql";
    }

    public UserApp selectUserApp(String userId, String appId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserApp"), ImmutableMap.of("userId", userId, "appId", appId));
    }

    public List<UserApp> selectUserAppList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserAppList"), userId);
    }

    public void insertUserApp(String userId, UserApp userApp) {
        sqlSessionTemplate.insert(getMappedSql("insertUserApp"), ImmutableMap.of("userId", userId, "userApp", userApp));
        sqlSessionTemplate.flushStatements();
    }

    public void updateUserApp(String userId, UserApp userApp) {
        sqlSessionTemplate.update(getMappedSql("updateUserApp"), ImmutableMap.of("userId", userId, "userApp", userApp));
        sqlSessionTemplate.flushStatements();
    }

    public void deleteUserApp(String userId, String appId) {
        sqlSessionTemplate.delete(getMappedSql("deleteUserApp"), ImmutableMap.of("userId", userId, "appId", appId));
    }
}
