package com.mplus.auth.config;

public enum AuthStatus {

	SUCCESS(900, "success"),
	UNAUTHORIZED(901, "未授权"),
	AUTH_DENIED(902, "权限不足"), 
	AUTH_SUCCESS(903, "认证成功"),
	LOGOUT_SUCCESS(904, "退出成功"),
	LOGIN_FAIL(905, "登录失败"),
	LOGIN_INCORRECT(906, "用户名或密码不正确"),
	LOGIN_TIMEOUT(907, "登录超时"),
	CAPTCHA_INCORRECT(908, "验证码不正确"),
	TOKEN_INVALID(909, "token无效"),
	NO_TOKEN(910, "没有token"),
	TOKEN_EXPIRED(911, "token过期");
	
	private final int code;
	private final String message;
	
	private AuthStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.code;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(this.code);
	}
	
}
