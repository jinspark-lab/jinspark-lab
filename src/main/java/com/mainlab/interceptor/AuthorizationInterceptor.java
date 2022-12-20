package com.mainlab.interceptor;

import com.mainlab.model.UserInfo;
import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.exception.ErrorCodes;
import com.mainlab.model.exception.TokenExpiredException;
import com.mainlab.service.AppLogService;
import com.mainlab.service.AuthService;
import com.mainlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private AppLogService appLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Every API operation should be validated by interceptor
        if (request.getMethod().equals("POST")) {
            return filterAuthorization(request, response, handler);
        }
        return super.preHandle(request, response, handler);
    }

    private boolean filterAuthorization(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtHeader = request.getHeader("authorization");

        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {
            try {
                UserInfo userInfo = authService.loadUserFromAuthToken(jwtHeader.substring(7));      // Verify Access token and get UserDetail from database.

                String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findAny()
                        .orElseThrow(() -> new AuthorizationException("Invalid Refresh Token", ErrorCode.UNAUTHORIZED)).getValue();

                // Check Refresh token is correct
                ErrorCodes.checkCondition(userInfo.getRefreshToken().equals(refreshToken), ErrorCode.UNAUTHORIZED, "Refresh token mismatch.", AuthorizationException.class);

                // Check Refresh token is Expired
                ErrorCodes.checkCondition(!authService.isExpired(refreshToken), ErrorCode.AUTH_OVERDUE, "Refresh Token expired.", TokenExpiredException.class);

                // Save User Info to SecurityContextHolder
                userService.setUserContextHolder(userInfo);
                appLogService.info("User has been logged in - " + userInfo.getUserId());

                return super.preHandle(request, response, handler);
            } catch (AuthorizationException e) {
                // Access token is expired, requests to call /refresh API
                appLogService.info("Access token is expired - " + jwtHeader.substring(7));
                response.setStatus(ErrorCode.UNAUTHORIZED.getCode());
            } catch (TokenExpiredException e) {
                // Refresh token is expired, requests re-login
                appLogService.info("Session Expired - " + jwtHeader.substring(7));
                response.setStatus(ErrorCode.AUTH_OVERDUE.getCode());
            }
        }
        return false;
    }
}
