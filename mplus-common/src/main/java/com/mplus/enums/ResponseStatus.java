package com.mplus.enums;

public enum ResponseStatus {
	SUCCESS(900, "success"), AUTH_NOT_PASSED(901, "authentication is not passed");

	private final int code;
	private final String desc;

	private ResponseStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
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
	public String getDesc() {
		return this.desc;
	}

	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(this.code);
	}
}
