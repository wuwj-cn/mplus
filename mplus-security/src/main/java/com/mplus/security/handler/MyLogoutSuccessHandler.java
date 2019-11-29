package com.mplus.security.handler;

import com.mplus.common.cache.utils.RedisKeys;
import com.mplus.common.cache.utils.RedisUtil;
import com.mplus.security.utils.AuthStatus;
import com.mplus.security.utils.Result;
import com.mplus.security.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户退出系统成功后 需要做的业务操作
 * @author wuwj
 *
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private UserUtils userUtils;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 根据token清空redis
		String userKey = RedisKeys.USER_KEY;
		String token = userUtils.getUserToken(request);
		redisUtil.hdel(userKey, token);
		SecurityContextHolder.clearContext(); // 清空上下文
		request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT"); // 从session中移除
		// 退出信息插入日志记录表中
		Result.sucess(response, AuthStatus.LOGOUT_SUCCESS, null);
	}

}
