package com.mainlab.controller;

import com.mainlab.model.content.UserSharablesRequest;
import com.mainlab.model.content.UserSharablesResponse;
import com.mainlab.model.response.SuccessResponse;
import com.mainlab.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/content")
public class ContentController extends BaseController {

    @Autowired
    private ContentService contentService;

    /***
     * Retrieve Content List
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public UserSharablesResponse listUserSharables() {
        return contentService.listUserSharables();
    }

    /***
     * Update Sharables State
     * @param userSharablesRequest
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public SuccessResponse updateUserSharables(@RequestBody UserSharablesRequest userSharablesRequest) {
        contentService.updateContentSharable(userSharablesRequest);
        return new SuccessResponse();
    }
}
