package com.mainlab.controller;

import com.mainlab.service.AlgorithmService;
import com.mainlab.service.MyTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private MyTableService myTableService;

    @Autowired
    private AlgorithmService algorithmService;

    @RequestMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Hello World");
    }

    @RequestMapping("/algo")
    public String algorithm() {
        algorithmService.run();
        return "Hello Algorithm";
    }
}
