package com.mainlab.repository;

import com.mainlab.model.UserContent;
import org.springframework.stereotype.Repository;

@Repository
public class UserContentRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserContentSql";
    }

    public UserContent selectUserContent(String userId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserContent"), userId);
    }
}
