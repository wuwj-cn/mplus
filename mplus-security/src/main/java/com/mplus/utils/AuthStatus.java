package com.mplus.utils;

public enum AuthStatus {

	UNAUTHORIZED(900, "未授权"),
	AUTH_DENIED(901, "权限不足"), 
	AUTH_SUCCESS(902, "认证成功"),
	LOGOUT_SUCCESS(903, "退出成功"),
	LOGIN_FAIL(904, "登录失败"),
	LOGIN_INCORRECT(905, "用户名或密码不正确"),
	LOGIN_TIMEOUT(906, "登录超时"),
	CAPTCHA_INCORRECT(907, "验证码不正确"),
	TOKEN_INVALID(908, "token无效"),
	NO_TOKEN(909, "没有token"),
	TOKEN_EXPIRED(910, "token过期");
	
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
