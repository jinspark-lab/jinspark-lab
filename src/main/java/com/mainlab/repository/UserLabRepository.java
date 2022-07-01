package com.mainlab.repository;

import com.mainlab.model.UserLab;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLabRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserLabSql";
    }

    public List<UserLab> selectUserLabList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserLabList"), userId);
    }
}
