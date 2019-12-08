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

package com.mplus.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mplus.common.enums.DataState;
import com.mplus.common.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Module;
import com.mplus.system.service.ModuleService;

@RestController
@RequestMapping(value = "/v1/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Module> add(@RequestBody Module module) {
		moduleService.save(module);
		return Result.success(module);
	}
	
	@RequestMapping(value = "/list/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Result<Page<Module>> list(
			@PathVariable int pageIndex, 
			@PathVariable int pageSize, 
			@RequestParam(required=false) String moduleName, 
			@RequestParam(required=false) String moduleCode, 
			@RequestParam(required=false) String status,
			@RequestParam(required=false) String sortProperties,
			@RequestParam(required=false) String sortDirection) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(moduleName)) searchParams.put("moduleName:like", moduleName);
		if(StringUtils.isNotBlank(moduleCode)) searchParams.put("moduleCode:like", moduleCode);
		if(StringUtils.isBlank(status)) {
			searchParams.put("status:ne", DataState.DELETED.getCode());
		} else {
			searchParams.put("status:eq", status);
		}
		
		List<String> properties = new ArrayList<String>();
		if(StringUtils.isNotBlank(sortProperties)) {
			properties.add(sortProperties);
		} else {
			properties.add("createDate"); //默认排序条件
		}
		
		Direction direction = Direction.DESC; //默认倒序
		if(StringUtils.isNotBlank(sortDirection) && sortDirection.equals("ascend")) {
			direction = Direction.ASC;
		}
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize, new Sort(direction, properties));
		Page<Module> modules = moduleService.findPage(searchParams, pageable);
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
		moduleService.update(module);
		return Result.success(module);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Result<String> delete(@PathVariable String id) {
		moduleService.delete(id);
		return Result.success(id);
	}
}
