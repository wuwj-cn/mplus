package com.mplus.common.enums;

import java.util.Objects;

public enum PermissionType {
	MENU("0", "菜单"), OPERATION("1", "功能操作"), FILE("2", "文件"), ELEMENT("3", "页面元素");
	
	private final String code;
	private final String name;

	private PermissionType(String code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public static PermissionType fromString(String code){
        Objects.requireNonNull(code, "value can not be null");
        switch (code) {
        case "0":
            return PermissionType.MENU;
        case "1":
            return PermissionType.OPERATION;
        case "2":
            return PermissionType.FILE;
        case "3":
            return PermissionType.ELEMENT;
        default:
            throw new IllegalArgumentException("code [" + code + "] not supported.");
        }
    }
}
