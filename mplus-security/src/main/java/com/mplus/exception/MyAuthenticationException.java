package com.mplus.exception;

import org.springframework.security.core.AuthenticationException;

import com.mplus.utils.AuthStatus;

public class MyAuthenticationException extends AuthenticationException {

	public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * 加入错误状态值
     * @param exceptionEnum
     */
    public MyAuthenticationException(AuthStatus exceptionEnum) {
        super(exceptionEnum.getMessage());
    }
}
