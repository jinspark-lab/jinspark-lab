package com.mainlab.controller;


import com.mainlab.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/algorithms")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    @RequestMapping("")
    public String algorithm() {
        algorithmService.run();
        return "Hello Algorithm";
    }
}
