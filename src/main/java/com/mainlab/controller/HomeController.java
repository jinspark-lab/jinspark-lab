package com.mainlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greeting() {
        int[] nums = {1, 2};
        int i = nums.length;

        return "Hello World";
    }

}
