package com.mplus.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mplus.security.utils.AuthStatus;
import com.mplus.security.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 认证失败 需要做的业务操作
 * 当检测到用户访问系统资源认证失败时则会进入到此类并执行相关业务
 * @author wuwj
 *
 */
@Slf4j
@Component
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求访问: ");
        msg.append(request.getRequestURI()).append(" 接口， 因为登录超时，无法访问系统资源.");
        log.info(msg.toString());
        Result.failure(response, AuthStatus.LOGIN_TIMEOUT);

      /*  boolean ajaxRequest = HttpUtils.isAjaxRequest(httpServletRequest);
        if (ajaxRequest){
            //如果是ajax请求 则返回自定义错误
            ResultUtil.writeJavaScript(httpServletResponse,ErrorCodeEnum.LOGIN,map);
        }else {
            // 非ajax请求 则跳转到指定的403页面
            //此处省略...................
        }*/
    }

}
