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
package com.mplus.system.entity;

import com.mplus.common.entity.BaseEntity;
import com.mplus.system.enums.RulePolicy;
import com.mplus.system.enums.RulePolicyConverter;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mp_sys_code_rule")
public class CodeRule extends BaseEntity {

    @Column(length = 32, nullable = false, unique = true)
    private String ruleId;

    @Column(length = 50, nullable = false)
    private String ruleName;

    @Column(length = 100)
    private String rulePrefix;

    @Column(length = 3, nullable = false)
    @Convert(converter = RulePolicyConverter.class)
    private RulePolicy rulePolicy;

    @Column(length = 5)
    private Integer serialLength;

    @Column(length = 13)
    private String currentValue;

    @Column(length = 255)
    private String remark;
}
