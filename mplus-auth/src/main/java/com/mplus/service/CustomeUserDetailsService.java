package com.mplus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomeUserDetailsService implements UserDetailsService {

	@Value("${mplus.cas.portal-enable}")
	boolean casPortalEnable;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("=========enter user details service load user method==============");
		
		if(casPortalEnable) {
			return loadUser(username);
		}
		
		// TODO 调用用户服务获取获取角色权限信息，当前暂时硬编码模拟
		CustomePasswordEncoder passwordEncode = new CustomePasswordEncoder();
		String pwd = passwordEncode.encode("NOT_USE_PWD");
		String[] authorities = {"USER", "query", "hello"};
		return User.withUsername(username).password(pwd).authorities(authorities).build();
	}
	
	// 获取用户信息
	private UserDetails loadUser(String username) {
		CustomePasswordEncoder passwordEncode = new CustomePasswordEncoder();
		String pwd = passwordEncode.encode("NOT_USE_PWD");
		String[] authorities = {};
		return User.withUsername(username).password(pwd).authorities(authorities).build();
	}

}
