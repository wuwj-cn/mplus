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
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "mp_sys_user")
public class User extends BaseEntity {
    @Column(length = 32, nullable = false, unique = true)
    private String userId;

    @Column(length = 50, nullable = false, unique = true)
    private String userName;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 50)
    private String nickName;

    @Column(length = 50)
    private String userAccount;

    @Column(length = 50)
    private String email;

    @Column(length = 15)
    private String mobile;

    @Column(length = 2)
    private String userStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Org org;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mp_sys_user_role_rel", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<Role>();

}
