package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.UserAppShortcut;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAppShortcutRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserAppShortcutSql";
    }

    public List<UserAppShortcut> selectUserAppShortcutList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserAppShortcutList"), userId);
    }

    public void insertUserAppShortcut(UserAppShortcut userAppShortcut) {
        sqlSessionTemplate.insert(getMappedSql("insertUserAppShortcut"), userAppShortcut);
        sqlSessionTemplate.flushStatements();
    }

    public void updateUserAppShortcut(UserAppShortcut userAppShortcut) {
        sqlSessionTemplate.update(getMappedSql("updateUserAppShortcut"), userAppShortcut);
        sqlSessionTemplate.flushStatements();
    }

    public void deleteUserAppShortcut(String userId, String appId) {
        sqlSessionTemplate.delete(getMappedSql("deleteUserAppShortcut"), ImmutableMap.of("userId", userId, "appId", appId));
        sqlSessionTemplate.flushStatements();
    }
}
