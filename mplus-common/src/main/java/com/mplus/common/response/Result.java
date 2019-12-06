package com.mplus.common.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 7212115037072836975L;
	
	private int code;
	private String message;
	private T data;

	@JSONField(name = "request_id")
	private String requestId;

	private Result() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		this.requestId = request.getHeader("requestId");
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
