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

package com.mplus.system.entity;

import com.mplus.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * DictData
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@Data
@Entity
@Table(name = "mp_sys_dict_data")
public class DictData extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String dictLabel;

    @Column(length = 50, nullable = false)
    private String dictValue;

    @Column(nullable = false)
    private Boolean buildIn;

    @ManyToOne
    private Dict dict;
}
