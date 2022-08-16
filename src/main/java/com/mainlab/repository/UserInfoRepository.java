package com.mainlab.repository;

import com.mainlab.model.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserInfoSql";
    }

    public UserInfo selectUserInfo(String userId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserInfo"), userId);
    }

    public UserInfo selectUserInfoByRefreshToken(String token) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserInfoByRefreshToken"), token);
    }

    public void insertUserInfo(String userId, String refreshToken) {
        sqlSessionTemplate.insert(getMappedSql("insertUserInfo"), new UserInfo(userId, refreshToken));
        sqlSessionTemplate.flushStatements();
    }
}
