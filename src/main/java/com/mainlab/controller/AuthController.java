package com.mainlab.controller;

import com.mainlab.model.response.SuccessResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/login/oauth2")
public class AuthController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/code/google")
    public HttpServletRequest googleLogin(HttpServletRequest httpServletRequest) {
        return httpServletRequest;
    }
}
