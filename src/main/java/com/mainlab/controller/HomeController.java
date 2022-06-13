package com.mainlab.controller;

import com.mainlab.model.Content;
import com.mainlab.service.AlgorithmService;
import com.mainlab.service.ContentService;
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

    @RequestMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Hello World");
    }

    @RequestMapping("/database")
    public ResponseEntity<String> databaseTest() {

        List<Content> contentList = contentService.getContentList();
        StringBuilder sb = new StringBuilder();
        for (Content content : contentList) {
            sb.append(content.getContentId()).append(":").append(content.getContentType()).append("\r\n");
        }
        return ResponseEntity.ok().body(sb.toString());
    }

    @RequestMapping("/algo")
    public String algorithm() {
        algorithmService.run();
        return "Hello Algorithm";
    }
}
