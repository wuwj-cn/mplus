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

package com.mplus.system.controller;

import com.mplus.common.response.Result;
import com.mplus.common.utils.jpa.JpaUtils;
import com.mplus.common.utils.jpa.QueryParam;
import com.mplus.common.utils.jpa.QueryType;
import com.mplus.system.entity.Module;
import com.mplus.system.repo.ModuleRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/module")
@AllArgsConstructor
public class ModuleController {

	private ModuleRepository moduleRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Module> add(@RequestBody Module module) {
		moduleRepository.save(module);
		return Result.success(module);
	}
	
	@RequestMapping(value = "/list/{pageNumber}/{pageSize}", method = RequestMethod.GET)
	public Result<Page<Module>> list(
			@PathVariable int pageNumber,
			@PathVariable int pageSize, 
			@RequestParam(required=false) String moduleName, 
			@RequestParam(required=false) String moduleCode) {
		List<QueryParam> searchParams = new ArrayList<>();
		QueryParam param = null;
		if(StringUtils.isNotBlank(moduleName)) {
			param = QueryParam.build("moduleName", QueryType.like, moduleName);
			searchParams.add(param);
		}
		if(StringUtils.isNotBlank(moduleCode)) {
			param = QueryParam.build("moduleCode", QueryType.like, moduleCode);
			searchParams.add(param);
		}

		List<String> properties = new ArrayList<>();
		properties.add("createTime"); //默认排序条件
		Direction direction = Direction.DESC; //默认倒序

		// pageable的页号是从0开始计算，因此根据传入的页号-1
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, new Sort(direction, properties));
		Specification<Module> spec = JpaUtils.buildSpecification(searchParams);
		Page<Module> modules = moduleRepository.findAll(spec, pageable);
		return Result.success(modules);
	}
	
	@RequestMapping(value = "/{moduleCode}", method = RequestMethod.GET)
	public Result<?> getOneByCode(@PathVariable String moduleCode) {
		return Result.success(null);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Module> update(@PathVariable String id, @RequestBody Module module) {
		if(!id.contentEquals(module.getId()))
			throw new RuntimeException("update object is not equals");
		moduleRepository.save(module);
		return Result.success(module);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result<String> delete(@PathVariable String id) {
		Module module = moduleRepository.findById(id).get();
		moduleRepository.delete(module);
		return Result.success(id);
	}
}
