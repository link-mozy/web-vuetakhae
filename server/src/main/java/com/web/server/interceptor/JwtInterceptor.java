package com.web.server.interceptor;

import com.web.server.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println(request.getMethod() + " : " + request.getServletPath());

        // option 요청은 바로 통과시켜주자.
        if (request.getMethod().equals("OPTION")) {
            return true;
        } else {
            // request의 parameter에서 auth_token으로 넘어온 녀석을 찾아본다.
            // String token = request.getParametr("auto_token");
            String token = request.getHeader("jwt-auth-token");
            if (token != null && token.length() > 0) {
                // 유효한 토큰이면 진행, 그렇지 않으면 예외를 발생시킨다.
                jwtService.checkValid(token);
                log.trace("토큰 사용 가능: {}", token);
                return true;
            } else {
                throw new RuntimeException("qweqweqw");
            }
        }
    }


}
