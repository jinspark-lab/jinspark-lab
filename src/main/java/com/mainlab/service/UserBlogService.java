package com.mainlab.service;

import com.mainlab.common.ObjectConvertService;
import com.mainlab.common.OperationType;
import com.mainlab.model.blog.UserBlog;
import com.mainlab.model.blog.UserBlogListResponse;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.exception.ErrorCodes;
import com.mainlab.repository.UserBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBlogService {
    @Autowired
    private UserBlogRepository userBlogRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AppLogService appLogService;
    @Autowired
    private ObjectConvertService objectConvertService;

    public UserBlogListResponse getUserBlogListResponse() {
        return new UserBlogListResponse(getUserBlogList());
    }

    public List<UserBlog> getUserBlogList() {
        String userId = userService.getOperationUserId(OperationType.READ);
        return userBlogRepository.selectUserBlogList(userId);
    }

    public UserBlog getUserBlog(int blogId) {
        String userId = userService.getOperationUserId(OperationType.READ);
        return getUserBlog(userId, blogId);
    }

    public UserBlog getUserBlog(String userId, int blogId) {
        UserBlog userBlog = userBlogRepository.selectUserBlog(userId, blogId);
        ErrorCodes.checkCondition(Optional.ofNullable(userBlog).isPresent(), ErrorCode.USER_BLOG_NOT_EXIST,
                "User Blog does not exist. userId=" + userId + " blogId=" + blogId);
        return userBlog;
    }

    public void recordUserBlog(UserBlog userBlog) {
        String userId = userService.getOperationUserId(OperationType.WRITE);
        if (userBlog.getBlogId() != 0) {
            // Update
            userBlogRepository.updateUserBlog(userId, userBlog);
            appLogService.info("Update User Blog : " + objectConvertService.objToString(userBlog));
        } else {
            // Generate new Blog ID
            List<UserBlog> userBlogList = userBlogRepository.selectUserBlogList(userId);
            int blogId = userBlogList.stream().mapToInt(UserBlog::getBlogId).max().orElse(0) + 1;
            userBlog.setBlogId(blogId);
            // Insert
            userBlogRepository.insertUserBlog(userId, userBlog);
            appLogService.info("Insert User Blog : " + objectConvertService.objToString(userBlog));
        }
    }

    public void deleteUserBlog(int blogId) {
        String userId = userService.getOperationUserId(OperationType.WRITE);
        userBlogRepository.deleteUserBlog(userId, blogId);
        appLogService.info("Delete User Blog : " + blogId);
    }
}
