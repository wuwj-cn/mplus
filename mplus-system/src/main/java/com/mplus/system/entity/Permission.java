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
package com.mplus.system.entity;

import com.mplus.common.entity.BaseEntity;
import com.mplus.system.enums.PermissionType;
import com.mplus.system.enums.PermissionTypeConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "mp_sys_permission")
public class Permission extends BaseEntity {

    @Column(length = 32, nullable = false, unique = true)
    private String permId;

    @Column(length = 50, nullable = false)
    private String permName;

    @Column(length = 2, nullable = false)
    @Convert(converter = PermissionTypeConverter.class)
    private PermissionType permType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mp_sys_role_perm_rel", joinColumns = {
            @JoinColumn(name = "perm_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    })
    private Set<Role> roles = new HashSet<Role>();
}
