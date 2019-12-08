/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.common.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum DataState {
	NORMAL("0", "正常"), DELETED("1", "删除"), DISABLED("2", "停用");

	private String code;
	private String name;

	private DataState(String code, String name) {
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

	private static final Map<String, DataState> MAP = new HashMap<String, DataState>();
    static {
        for (DataState e : DataState.values()) {
            MAP.put(e.getCode(), e);
        }
    }
    
	public static DataState fromString(String code) {
		Objects.requireNonNull(code, "value can not be null");
		DataState e = MAP.get(code);
		if(null == e) throw new IllegalArgumentException("code [" + code + "] not supported.");
		return e;
	}

//	@Override
//	public String toString() {
//		// "dataState":{"code":"0","name":"正常"}
//		return "{\"code\":\"" + code + "\", \"name\":\"" + name + "\"}";
//	}
}
