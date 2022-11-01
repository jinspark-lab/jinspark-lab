package com.mainlab.controller;

import com.mainlab.model.UserApp;
import com.mainlab.model.UserAppRequest;
import com.mainlab.model.UserAppShortcutResponse;
import com.mainlab.model.response.SuccessResponse;
import com.mainlab.service.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/userapp")
public class UserAppController extends BaseController {

    @Autowired
    private UserAppService userAppService;

    @ResponseBody
    @RequestMapping(value = "/shortcuts", method = RequestMethod.POST)
    public UserAppShortcutResponse getUserAppShortcuts() {
        return new UserAppShortcutResponse(userAppService.getUserAppShortcutList());
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public UserApp getUserAppDetail(@RequestParam(value = "appId") String appId) {
        return userAppService.getUserAppDetail(appId);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public SuccessResponse insertUserApp(@RequestBody UserAppRequest userAppRequest) {
//        userAppService.addUserApp(userAppRequest);
        return new SuccessResponse();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateUserApp(@RequestBody UserAppRequest userAppRequest) {

    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteUserApp(@RequestBody UserAppRequest userAppRequest) {

    }
}
