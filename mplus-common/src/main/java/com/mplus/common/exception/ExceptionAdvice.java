package com.mplus.common.exception;

import com.mplus.common.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("参数解析失败", e);
		return Result.failure(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("不支持当前请求方法", e);
		return Result.failure(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Result handleHttpMediaTypeNotSupportedException(Exception e) {
		logger.error("不支持当前媒体类型", e);
		return Result.failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), e.getMessage());
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error("服务运行异常", e);
		return Result.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
