package com.mplus.common.enums;

import java.util.Objects;

public enum UserState {

	ENABLE("0", "正常"), DISABLED("1", "停用");
	
	private final String code;
	private final String name;

	private UserState(String code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static UserState fromString(String code){
        Objects.requireNonNull(code, "value can not be null");
        switch (code) {
        case "0":
            return UserState.ENABLE;
        case "1":
            return UserState.DISABLED;
        default:
            throw new IllegalArgumentException("code [" + code + "] not supported.");
        }
    }
}
