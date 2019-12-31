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

package com.mplus.system.vo;

import com.mplus.common.vo.BaseVo;
import lombok.Data;

/**
 * MenuVo
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@Data
public class MenuVo extends BaseVo {
    private String menuCode;
    private String menuName;
    private String url;
    private String parentMenuCode;
    private String parentMenuName;
    private String moduleCode;
    private String moduleName;
}
