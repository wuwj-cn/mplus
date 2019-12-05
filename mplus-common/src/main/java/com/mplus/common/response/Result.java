package com.mplus.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 7212115037072836975L;
	
	private int code;
	private String message;
	private T data;

	private Result() {
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<T>();
		result.setCode(ResponseStatus.SUCCESS.value());
		result.setMessage(ResponseStatus.SUCCESS.getReasonPhrase());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> failure(int code, String msg) {
		Result<T> result = new Result<T>();
		result.setCode(code);
		result.setMessage(msg);
		result.setData(null);
		return result;
	}
}
