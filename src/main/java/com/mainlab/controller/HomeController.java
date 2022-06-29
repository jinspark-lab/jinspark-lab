package com.mainlab.controller;

import com.mainlab.model.UserContent;
import com.mainlab.service.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class HomeController {

    @Autowired
    private UserContentService userContentService;

    //TODO: Add Login & Authorization process in the queue
    @ResponseBody
    @RequestMapping(value = "/")
    public UserContent showMyAccountPage(@RequestParam("userId") String userId) {
        return userContentService.getUserContent(userId);
    }

    @RequestMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("PING");
    }
}
