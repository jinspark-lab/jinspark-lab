package com.mainlab.controller;

import com.mainlab.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class HomeController {

    @ResponseBody
    @RequestMapping(value = { "", "/profile" })
    public void getProfileContent() {

    }

    @ResponseBody
    @RequestMapping(value = "/lab")
    public void getLabContent() {

    }

    @RequestMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("PING");
    }
}
