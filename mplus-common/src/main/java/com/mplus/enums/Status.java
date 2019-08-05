package com.mplus.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Status {
	NORMAL("0", "正常"), DELETED("1", "删除"), DISABLED("2", "停用");

	private String code;
	private String name;

	private Status(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final Map<String, Status> MAP = new HashMap<String, Status>();
    static {
        for (Status e : Status.values()) {
            MAP.put(e.getCode(), e);
        }
    }
    
	public static Status fromString(String code) {
		Objects.requireNonNull(code, "value can not be null");
		Status e = MAP.get(code);
		if(null == e) throw new IllegalArgumentException("code [" + code + "] not supported.");
		return e;
	}

//	@Override
//	public String toString() {
//		// "dataState":{"code":"0","name":"正常"}
//		return "{\"code\":\"" + code + "\", \"name\":\"" + name + "\"}";
//	}
}
