package com.mplus.zuul.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求资源权限不足处理
 * @author wuwj
 *
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求: ")
        					.append(request.getRequestURI())
        					.append(", 资源权限不足.");
        log.info(msg.toString());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
