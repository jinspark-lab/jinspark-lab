package com.mainlab.controller;

import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.login.JwtRequest;
import com.mainlab.model.login.JwtResponse;
import com.mainlab.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/login/oauth2")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping(value = "/code/google", method = RequestMethod.POST)
    public JwtResponse googleLogin(@RequestBody JwtRequest jwtRequest) {
        return authService.loginGoogle(jwtRequest.getCredential());
    }

    @ResponseBody
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public JwtResponse refreshToken(@CookieValue("refreshToken") String refreshToken) {
        // Generate Access token, and update Refresh token if it is expired.
        return authService.refreshUserAccess(refreshToken);
    }
}
