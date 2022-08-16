package com.mainlab.interceptor;

import com.mainlab.model.UserInfo;
import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("POST")) {
            return filterAuthorization(request, response, handler);
        }
        return super.preHandle(request, response, handler);
    }

    private boolean filterAuthorization(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtHeader = request.getHeader("authorization");

        if (jwtHeader != null && jwtHeader.startsWith("Bearer ")) {

            try {
                UserInfo userInfo = authService.loadUserFromAuthToken(jwtHeader.substring(7));

                String refreshToken = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refreshToken")).findFirst()
                        .orElseThrow(() -> new AuthorizationException("Invalid Refresh Token", ErrorCode.UNAUTHORIZED)).getValue();

                // UserID 를 이용해서 UserDetailService 에서 유저 정보를 조회
                System.out.println("User ID : " + userInfo.getUserId());

                // 쿠키의 Refresh Token 과 DB 의 Refresh Token 이 일치하는지 체크

                System.out.println("Refresh Token : " + refreshToken);

                // 일치하지 않았거나 Refresh Token 이 Expired 되었다면 예외를 출력해서 재로그인을 유도

                // 일치하고, Refresh Token 이 Expired 되지 않았다면 Access Token 을 검사

                // Access Token 이 Expire 되었다면 예외를 던지고 /refresh API 호출을 유도

                // /refresh API 를 통해 새로운 Access Token 을 발급
                return super.preHandle(request, response, handler);
            } catch (AuthorizationException e) {
                System.out.println(e.getMessage());
            }
        }
        response.setStatus(ErrorCode.UNAUTHORIZED.getCode());
        return false;
//        throw new AuthorizationException("Invalid User Token", ErrorCode.UNAUTHORIZED);
    }
}
