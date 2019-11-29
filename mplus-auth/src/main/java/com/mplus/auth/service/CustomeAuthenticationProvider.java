package com.mplus.auth.service;

import java.util.Map;

import com.mplus.auth.utils.JschUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义权限认证
 * @author wuwj
 *
 */
@Slf4j
public class CustomeAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Value("${mplus.cas.portal-enable: false}")
	boolean casPortalEnable;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(casPortalEnable) {
			return CustomeAuthenticate(authentication);
		}
		return super.authenticate(authentication);
	}
	
	// 自定义认证
	private Authentication CustomeAuthenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
				() -> messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.onlySupports",
						"Only UsernamePasswordAuthenticationToken is supported"));

		// Determine username
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
				: authentication.getName();
		
		Boolean isAuthorized = false;
		try {
			isAuthorized = casAuthorize(username, authentication.getCredentials().toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(!isAuthorized) {
			throw new AuthenticationServiceException("Custome CAS authorization failure");
		}
		
		UserDetails user = null;
		try {
			user = retrieveUser(username,
					(UsernamePasswordAuthenticationToken) authentication);
		}
		catch (UsernameNotFoundException notFound) {
			log.debug("User '" + username + "' not found");

			if (hideUserNotFoundExceptions) {
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials",
						"Bad credentials"));
			}
			else {
				throw notFound;
			}
		}

		Assert.notNull(user,
				"retrieveUser returned null - a violation of the interface contract");

		return createSuccessAuthentication(user.getUsername(), authentication, user);
	}

	static String DNA_SHELL = "";
	
	private boolean casAuthorize(String username, String password) throws Exception {
		JschUtil jschUtil = JschUtil.getInstance();
		JSONObject json = null;
        if(jschUtil.connect()){
             /*执行命令*/
            String cmd = String.format(DNA_SHELL, username, password);
            Map<String,Object> result = jschUtil.execCmmmand(cmd);
            log.debug(result.get("result").toString());
            json = JSONObject.parseObject(result.get("result").toString());
            jschUtil.close();
        }
        
        Boolean isAuthorized = false;
        if(null != json) {
        	isAuthorized = json.getBoolean("success");
        }
        return isAuthorized;
	}

}
