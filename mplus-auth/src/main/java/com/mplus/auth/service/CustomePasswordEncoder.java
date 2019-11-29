package com.mplus.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 自定义密码处理
 * @author wuwj
 *
 */
public class CustomePasswordEncoder extends BCryptPasswordEncoder {

	@Value("${mplus.cas.portal-enable: false}")
	boolean casPortalEnable;

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if(casPortalEnable) {
			// 不做密码匹配
			return true;
		}
		return super.matches(rawPassword, encodedPassword);
	}

}
