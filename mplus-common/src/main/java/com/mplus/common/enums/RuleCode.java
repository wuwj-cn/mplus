package com.mplus.common.enums;

import java.util.Objects;

public enum RuleCode {

	USER("USER", "用户"), ROLE("ROLE", "角色");
	
	private final String code;
	private final String name;

	private RuleCode(String code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static RuleCode fromString(String code){
        Objects.requireNonNull(code, "value can not be null");
        switch (code) {
        case "USER":
            return RuleCode.USER;
        default:
            throw new IllegalArgumentException("code [" + code + "] not supported.");
        }
    }
}
