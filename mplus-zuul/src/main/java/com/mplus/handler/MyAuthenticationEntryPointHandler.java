package com.mplus.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求资源鉴权失败处理
 * @author wuwj
 *
 */
@Slf4j
@Component
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求访问: ")
        					.append(request.getRequestURI())
        					.append(" , 网关鉴权失败.");
        log.info(msg.toString());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        response.sendRedirect("/auth/api/v1/login");
    }

}
