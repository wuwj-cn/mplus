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

package com.mplus.system.enums;

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
