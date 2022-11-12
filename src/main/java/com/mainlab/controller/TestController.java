package com.mainlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * TestController to support QA.
 * DO NOT IMPLEMENT LOGIC via Test API
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {

}
