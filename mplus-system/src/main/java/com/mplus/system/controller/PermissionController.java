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

package com.mplus.system.controller;

import com.mplus.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Permission;
import com.mplus.system.service.PermissionService;

@RestController
@RequestMapping(value = "/v1/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Permission> add(@RequestBody Permission permission) {
		permissionService.savePermission(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Permission> update(@PathVariable String id, @RequestBody Permission permission) {
		if(!id.contentEquals(permission.getId()))
			throw new RuntimeException("update object is not equals");
		permissionService.updatePermission(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.DELETE)
	public Result<Permission> remove(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		permissionService.removePermission(permission);
		return Result.success(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.GET)
	public Result<Permission> getOne(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		return Result.success(permission);
	}
}
