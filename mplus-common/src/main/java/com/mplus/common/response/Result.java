/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
