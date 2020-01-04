package com.koreahacks.govis.interceptor;

import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String accessToken = request.getHeader("authorization");

        if ("".equals(accessToken)) {

            return false;
        }

        try {
            request.setAttribute("userId", authService.verifyAccessToken(accessToken));

            return true;
        } catch (GovisException e) {

            return false;
        }
    }
}
