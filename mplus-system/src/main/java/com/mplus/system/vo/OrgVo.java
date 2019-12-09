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

package com.mplus.system.vo;

import lombok.Data;

/**
 * OrgVo
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@Data
public class OrgVo {
    private String orgId;
    private String orgName;
    private String fullName;
    private String parentOrgId;
    private String parentOrgName;
    private String remark;
}
