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

import com.mplus.common.enums.DataState;
import com.mplus.common.response.Result;
import com.mplus.system.repo.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Permission;

@RestController
@RequestMapping(value = "/v1/permission")
@AllArgsConstructor
public class PermissionController {

	@Autowired
	private PermissionRepository permissionRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Permission> add(@RequestBody Permission permission) {
		permissionRepository.save(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Permission> update(@PathVariable String id, @RequestBody Permission permission) {
		if(!id.contentEquals(permission.getId()))
			throw new RuntimeException("update object is not equals");
		permissionRepository.save(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.DELETE)
	public Result<Permission> remove(@PathVariable String permissionCode) {
		Permission permission = permissionRepository.findOneByCode(permissionCode, DataState.NORMAL.code());
		permissionRepository.save(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.GET)
	public Result<Permission> getOne(@PathVariable String permissionCode) {
		Permission permission = permissionRepository.findOneByCode(permissionCode, DataState.NORMAL.code());
		return Result.success(permission);
	}
}
