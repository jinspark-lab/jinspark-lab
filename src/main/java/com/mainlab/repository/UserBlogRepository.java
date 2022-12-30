package com.mainlab.repository;

import com.google.common.collect.ImmutableMap;
import com.mainlab.model.blog.UserBlog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserBlogRepository extends BaseRdbDaoSupport {
    @Override
    protected String getNamespace() {
        return "UserBlogSql";
    }

    public UserBlog selectUserBlog(String userId, int blogId) {
        return sqlSessionTemplate.selectOne(getMappedSql("selectUserBlog"), ImmutableMap.of("userId", userId, "blogId", blogId));
    }

    public List<UserBlog> selectUserBlogList(String userId) {
        return sqlSessionTemplate.selectList(getMappedSql("selectUserBlogList"), userId);
    }

    public void insertUserBlog(String userId, UserBlog userBlog) {
        sqlSessionTemplate.insert(getMappedSql("insertUserBlog"), ImmutableMap.of("userId", userId, "userBlog", userBlog));
        sqlSessionTemplate.flushStatements();
    }

    public void updateUserBlog(String userId, UserBlog userBlog) {
        sqlSessionTemplate.update(getMappedSql("updateUserBlog"), ImmutableMap.of("userId", userId, "userBlog", userBlog));
        sqlSessionTemplate.flushStatements();
    }

    public void deleteUserBlog(String userId, int blogId) {
        sqlSessionTemplate.delete(getMappedSql("deleteUserBlog"), ImmutableMap.of("userId", userId, "blogId", blogId));
    }
}
