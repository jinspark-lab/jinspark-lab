package com.mainlab.repository;

import com.mainlab.model.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public class UserProfileRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserProfileSql";
    }

    public UserProfile selectUserProfile(String userId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserProfile"), userId);
    }

    //TODO: Need to fix to use userId more clean
    public void updateUserProfile(String userId, UserProfile userProfile) {
        sqlSessionTemplate.update(getMappedSql("updateUserProfile"), userProfile);
    }
}
