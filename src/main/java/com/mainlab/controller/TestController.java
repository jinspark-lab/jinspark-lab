package com.mainlab.controller;

import com.mainlab.model.response.SuccessResponse;
import com.mainlab.service.AppLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/***
 * TestController to support QA.
 * DO NOT IMPLEMENT LOGIC via Test API
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {

    @Autowired
    private AppLogService appLogService;

    @ResponseBody
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public SuccessResponse testLog() {
        appLogService.test("Hello World Test");
        return new SuccessResponse();
    }
}
