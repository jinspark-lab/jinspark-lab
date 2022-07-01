package com.mainlab.controller;

import com.mainlab.model.UserContent;
import com.mainlab.service.ContentService;
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
    private ContentService contentService;
    @Autowired
    private UserContentService userContentService;

    @RequestMapping(value = "/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok().body("Hello World");
    }

    //TODO: Add Login & Authorization process in the queue
    @ResponseBody
    @RequestMapping(value = "/account")
    public UserContent showMyAccountPage(@RequestParam("userId") String userId) {
        return userContentService.getUserContent(userId);
    }

    @RequestMapping(value = "/check")
    public ResponseEntity<String> checkEnvironment() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(contentService.getContentList());
            return ResponseEntity.ok().body(sb.toString());
        } catch(Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("PING");
    }
}
