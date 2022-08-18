package com.mainlab.repository;

import com.mainlab.model.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserRoleSql";
    }

    public List<UserRole> selectUserRoleList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserRoleList"), userId);
    }

    public void insertUserRoleList(String userId, List<UserRole> userRoleList) {
        if (!userRoleList.isEmpty()) {
            userRoleList.forEach(userRole -> {
                sqlSessionTemplate.insert(getMappedSql("insertUserRoleList"), userRole);
            });
            sqlSessionTemplate.flushStatements();
        }
    }
}
