package com.mplus.common.response;

import java.io.Serializable;

public class Result<T> implements Serializable {
	private static final long serialVersionUID = 7212115037072836975L;
	
	private int status;
	private String msg;
	private T data;

	private Result() {
	}

	public static <T> Result<T> sucess(T data) {
		Result<T> result = new Result<T>();
		result.setStatus(ResponseStatus.SUCCESS.value());
		result.setMsg(ResponseStatus.SUCCESS.getDesc());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> failure(int stauts, String msg) {
		Result<T> result = new Result<T>();
		result.setStatus(stauts);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
