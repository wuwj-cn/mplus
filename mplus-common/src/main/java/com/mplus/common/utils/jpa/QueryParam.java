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
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.common.utils.jpa;

import lombok.Data;

/**
 * QueryParam
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@Data
public class QueryParam {
    private String name;
    private QueryType queryType;
    private Object value;

    private QueryParam(String name, QueryType queryType, Object value){
        this.name = name;
        this.queryType = queryType;
        this.value = value;
    }

    public static QueryParam build(String name, QueryType queryType, Object value) {
        return new QueryParam(name, queryType, value);
    }
}