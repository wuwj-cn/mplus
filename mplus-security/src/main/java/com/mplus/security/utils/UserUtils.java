package com.mplus.security.utils;

import javax.servlet.http.HttpServletRequest;

import com.mplus.security.exception.MyAuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserUtils {

	@Value("${jwt.header}")
    private String header;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    public String getUserToken(HttpServletRequest request) {
    	String token = "";
    	String authHeader = request.getHeader(this.header);
    	if (authHeader != null && authHeader.startsWith(tokenHead)) {
    		token = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            log.info("请求"+request.getRequestURI()+", 携带的token值：" + token);
    	} else {
    		log.info("token 无效.");
            throw  new MyAuthenticationException(AuthStatus.TOKEN_INVALID);
    	}
    	return token;
    }
    
    public String getUserToken(ServerHttpRequest request) {
    	String token = "";
    	String authHeader = request.getHeaders().getFirst(this.header);
    	if (authHeader != null && authHeader.startsWith(tokenHead)) {
    		token = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            log.info("请求"+request.getURI()+", 携带的token值：" + token);
    	} else {
    		log.info("token 无效.");
            throw  new MyAuthenticationException(AuthStatus.TOKEN_INVALID);
    	}
    	return token;
    }
}
