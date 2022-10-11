package com.mainlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * SystemController is for Internal Usages (System Check & Health Ping & Dev & Testing), and should not be exposed from public URL.
 */
@RestController
@RequestMapping(value = "")
public class SystemController extends BaseController {

    @RequestMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("PING");
    }
}
