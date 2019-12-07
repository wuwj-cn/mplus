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

package com.mplus.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

public interface BaseService<T, ID extends Serializable> {
	
	/**
	 * 新增
	 */
	T save(T t);
	
	/**
	 * 批量新增
	 * 注意数量不要太大，特别是数据迁移时不要使用该方法
	 */
	Iterable<T> save(Iterable<T> entities);
	
	/**
	 * 更新
	 */
	T update(T t);

	/**
	 * 批量更新
	 */
	Iterable<T> update(Iterable<T> entities);
	
	/**
	 * 根据ID删除
	 */
	void delete(ID id);
	
	/**
	 * 根据实体删除
	 */
	void delete(T t);
	
	/**
	 * 根据ID查找对象
	 */
	Optional<T> findById(ID id);
	
	List<T> findAll();
	
	/**
	 * 分页排序获取数据
	 * 禁止使用该接口进行count操作
	 * Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"id"));
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable);
	
	/**
	 * 多条件查询
	 * 注：多个条件间是and关系 & 参数是属性对应的类型 使用时注意避免结果集过大
	 * @param params {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
	 * @return
	 */
	List<T> find(@Nullable Map<String, Object> params);
	
	/**
	 * 分页多条件查询
	 * 注：多个条件间是and关系 & 参数是属性对应的类型
	 * @param params {"username:like":"test"} 键的格式为字段名:过滤方式,过滤方式见{@code QueryTypeEnum}
	 * @param pageable 分页信息 new PageRequest(page, size,new Sort(Direction.DESC, "updateTime"))
	 * @return
	 */
	Page<T> findPage(Map<String, Object> params,Pageable pageable);
	
	/**
	 * Returns all entities matching the given {@link Map} and {@link Sort}.
	 *
	 * @param params can be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return never {@literal null}.
	 */
	List<T> find(@Nullable Map<String, Object> params, Sort sort);
}
