package com.mplus.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.mplus.utils.AuthStatus;
import com.mplus.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录系统失败后 需要做的业务操作
 * 当用户登录系统失败后则会进入到此类并执行相关业务
 * @author wuwj
 *
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //用户登录时身份认证未通过
        if (e instanceof BadCredentialsException){
        	String errorMsg = "用户登录时：用户名或者密码错误.";
            log.info(errorMsg);
            Result.failure(response, AuthStatus.LOGIN_INCORRECT);
        }else{
        	String errorMsg = "用户登录失败";
            Result.failure(response, AuthStatus.LOGIN_FAIL);
        }
    }

}
