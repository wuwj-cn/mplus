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

package com.mplus.common.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mplus.common.utils.jpa.JpaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.mplus.common.entity.BaseEntity;
import com.mplus.common.enums.DataState;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.BaseService;

public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements BaseService<T, ID> {

	public abstract BaseRepository<T, ID> getRepository();
	
	@Override
	public T save(T t){
		if(StringUtils.isNotBlank(t.getId())) {
			throw new RuntimeException("object id is not null");
		}
//		User user = UserUtils.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
//		t.setCreateBy(user.getId());
		t.setCreateTime(now);
//		t.setUpdateBy(user.getId());
		t.setModifyTime(now);
		t.setDataState(DataState.NORMAL.code());
		return getRepository().save(t);
	}
	
	@Override
	public Iterable<T> save(Iterable<T> entities){
//		User user = UserUtils.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
		for (T t : entities) {
			if(StringUtils.isNotBlank(t.getId())) {
				throw new RuntimeException("object id is not null");
			}
//			t.setCreateBy(user.getId());
			t.setCreateTime(now);
//			t.setUpdateBy(user.getId());
			t.setModifyTime(now);
			t.setDataState(DataState.NORMAL.code());
		}
		return getRepository().saveAll(entities);
	}
	
	@Override
	public T update(T t) {
		if (StringUtils.isBlank(t.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
//		User user = UserUtils.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
//		t.setUpdateBy(user.getId());
		t.setModifyTime(now);
		return getRepository().save(t);
	}

	@Override
	public Iterable<T> update(Iterable<T> entities) {
//		User user = UserUtils.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
		for (T t : entities) {
			if(StringUtils.isBlank(t.getId())) {
				throw new RuntimeException("object id is null or empty");
			}
//			t.setUpdateBy(user.getId());
			t.setModifyTime(now);
		}
		return getRepository().saveAll(entities);
	}
	
	@Override
	public void delete(ID id){
		Optional<T> t = findById(id);
		if(t == null){
			return;
		}
		delete(t.get());
	}
	
	@Override
	public void delete(T t) {
//		User user = UserUtils.getCurrentUser();
		LocalDateTime now = LocalDateTime.now();
//		t.setUpdateBy(user.getId());
		t.setModifyTime(now);
		t.setDataState(DataState.DELETED.code());
		getRepository().save(t);
	}
	
	@Override
	public Optional<T> findById(ID id) {
		return getRepository().findById(id);
	}
	
	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}
	
	@Override
	public Page<T> findAll(Pageable pageable){
		return getRepository().findAll(pageable);
	}
	
	@Override
	public List<T> findAll(@Nullable final Map<String, Object> params) {
		Specification<T> spec = JpaUtils.buildSpecification(params);
		List<T> list = getRepository().findAll(spec);
		return list;
	}
	
	@Override
	public Page<T> findPage(@Nullable final Map<String, Object> params,Pageable pageable){
		Specification<T> spec = JpaUtils.buildSpecification(params);
		Page<T> page = getRepository().findAll(spec, pageable);
		return page;
	}
	
	public List<T> findAll(@Nullable Map<String, Object> params, Sort sort) {
		Specification<T> spec = JpaUtils.buildSpecification(params);
		List<T> list = getRepository().findAll(spec, sort);
		return list;
	}

}
