package com.mplus.common.enums;

import java.util.Objects;

public enum RulePolicy {
	SERIAL("0", "流水号"), // 流水号
	DATE("1", "日期"), // 日期
	DATE_SERIAL("2", "日期流水号"); // 日期+流水号
	
	private final String code;
	private final String name;
	
	private RulePolicy(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static RulePolicy fromString(String code){
        Objects.requireNonNull(code, "value can not be null");
        switch (code) {
        case "0":
            return RulePolicy.SERIAL;
        case "1":
            return RulePolicy.DATE;
        case "2":
            return RulePolicy.DATE_SERIAL;
        default:
            throw new IllegalArgumentException("code [" + code + "] not supported.");
        }
    }
}
