package com.mainlab.controller;

import com.mainlab.model.UserLabResponse;
import com.mainlab.service.UserLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/lab")
public class LabController {

    @Autowired
    private UserLabService userLabService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserLabResponse getUserLab() {
        return userLabService.getUserLabResponse();
    }
}
