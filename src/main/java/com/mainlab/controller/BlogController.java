package com.mainlab.controller;

import com.mainlab.model.blog.UserBlog;
import com.mainlab.model.blog.UserBlogListResponse;
import com.mainlab.model.response.SuccessResponse;
import com.mainlab.service.UserBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/userblog")
public class BlogController extends BaseController {
    @Autowired
    private UserBlogService userBlogService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public UserBlogListResponse getUserBlogList() {
        return userBlogService.getUserBlogListResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/content/{blogId}", method = RequestMethod.POST)
    public UserBlog getUserBlogContent(@PathVariable(value = "blogId") int blogId) {
        return userBlogService.getUserBlog(blogId);
    }

    @ResponseBody
    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public SuccessResponse recordUserBlogContent(@RequestBody UserBlog userBlog) {
        userBlogService.recordUserBlog(userBlog);
        return new SuccessResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{blogId}", method = RequestMethod.POST)
    public SuccessResponse deleteUserBlogContent(@PathVariable(value = "blogId") int blogId) {
        userBlogService.deleteUserBlog(blogId);
        return new SuccessResponse();
    }
}
