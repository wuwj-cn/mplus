package com.mplus.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mplus.jwt.JWTUser;
import com.mplus.jwt.JwtTokenUtil;
import com.mplus.redis.RedisKeys;
import com.mplus.redis.RedisUtil;
import com.mplus.utils.AuthStatus;
import com.mplus.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录系统成功后 需要做的业务操作
 * 当用户登录系统成功后则会进入到此类并执行相关业务
 * @author wuwj
 *
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得授权后可得到用户信息(非jwt 方式)
        //User userDetails =  (User) authentication.getPrincipal();

        //获得授权后可得到用户信息(jwt 方式)
        JWTUser user =  (JWTUser) authentication.getPrincipal();
        //将身份 存储到SecurityContext里
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        StringBuffer msg = new StringBuffer("用户：");
        msg.append(user.getUsername()).append(" 成功登录系统.");
        log.info(msg.toString());
        
        //使用jwt生成token 用于权限效验
        String token = jwtTokenUtil.generateAccessToken(user);
//        UserDetail user = userDetails.getUserInfo();
//        user.setToken(token);
        
        //将登录人信息放在redis中
        this.saveTokenToRedis(user.getUserId(),token,JSON.toJSONString(user));
        
        String access_token = tokenHead + token;
//        String refresh_token = tokenHead + jwtTokenUtil.refreshToken(token);
        Map<String,String> map = new HashMap<>();
        map.put("token", access_token);
//        map.put("refresh_token", refresh_token);
        map.put("userId",user.getUserId().toString());
        map.put("userName",user.getUsername());
//        map.put("email",user.getEmail());
//        map.put("msage",msg.toString());
        Result.sucess(response, AuthStatus.AUTH_SUCCESS, map);
    }

    /**
     * 将用户token 和用户信息 保存到redis中
     * @param userId  用户id
     * @param token   用户token
     * @param value   用户信息
     */
    private void saveTokenToRedis(String userId,String token,String value){
        String userKey =  RedisKeys.USER_KEY;
        redisUtil.hset(userKey,token,value,expiration);
    }

}
