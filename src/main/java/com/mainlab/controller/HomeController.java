package com.mainlab.controller;

import com.mainlab.model.Content;
import com.mainlab.service.AlgorithmService;
import com.mainlab.service.ContentService;
import com.mainlab.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private ProfileService profileService;

    @RequestMapping("/env")
    public ResponseEntity<String> getEnvProfile() {
        return ResponseEntity.ok().body(profileService.getProfile());
    }

    @RequestMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Hello World");
    }

    @RequestMapping("/database")
    public ResponseEntity<String> databaseTest() {
        try {
            List<Content> contentList = contentService.getContentList();
            StringBuilder sb = new StringBuilder();
            for (Content content : contentList) {
                sb.append(content.getContentId()).append(":").append(content.getContentType()).append("\r\n");
            }
            return ResponseEntity.ok().body(sb.toString());
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @RequestMapping("/algo")
    public String algorithm() {
        algorithmService.run();
        return "Hello Algorithm";
    }
}
