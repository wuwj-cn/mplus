package com.mplus.common.response;

public enum ResponseStatus {
	SUCCESS(900, "success"), AUTH_NOT_PASSED(901, "authentication is not passed");

	private final int value;
	private final String reasonPhrase;

	private ResponseStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
}
