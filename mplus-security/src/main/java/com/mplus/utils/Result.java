package com.mplus.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class Result<T> implements Serializable {
	private static final long serialVersionUID = -5395024005454085630L;
	
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String CONTENT_TYPE = "application/json; charset=utf-8";
	
	private int status;
	private String msg;
	private T data;

	private Result() {
	}

	public static <T> void sucess(HttpServletResponse response, AuthStatus status, T data) {
		try {
//			response.setStatus(HttpStatus.SC_OK);
			response.setCharacterEncoding(CHAR_ENCODING);
			response.setContentType(CONTENT_TYPE);
			PrintWriter printWriter = response.getWriter();
			Result<T> result = new Result<T>();
			result.setStatus(status.value());
			result.setMsg(status.getMessage());
			result.setData(data);
			String body = JSON.toJSONString(result);
			printWriter.write(body);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static <T> void failure(HttpServletResponse response, AuthStatus status) {
		try {
			response.setCharacterEncoding(CHAR_ENCODING);
			response.setContentType(CONTENT_TYPE);
			PrintWriter printWriter = response.getWriter();
			Result<T> result = new Result<T>();
			result.setStatus(status.value());
			result.setMsg(status.getMessage());
			result.setData(null);
			String body = JSON.toJSONString(result);
			printWriter.write(body);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
