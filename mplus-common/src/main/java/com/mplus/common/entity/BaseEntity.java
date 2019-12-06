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
package com.mplus.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mplus.common.listeners.BaseEntityListener;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(value = {BaseEntityListener.class})
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
	
	@Id
    @Column(length=64)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(length=64)
	private String creatorId; // 插入人

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime; // 插入时间

	@Column(length=64)
	private String operatorId; // 修改人

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime; // 修改时间

	@Column(length=2, nullable = false)
	private String dataState; // 状态, 0正常 1删除 2停用

}
