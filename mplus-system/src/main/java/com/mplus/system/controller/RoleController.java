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
import com.mplus.system.repo.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Role;

@RestController
@RequestMapping(value = "/v1/role")
@AllArgsConstructor
public class RoleController {

	private RoleRepository roleRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Role> add(@RequestBody Role role) {
		roleRepository.save(role);
		return Result.success(role);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Role> update(@PathVariable String id, @RequestBody Role role) {
		if(!id.contentEquals(role.getId()))
			throw new RuntimeException("update object is not equals");
		roleRepository.save(role);
		return Result.success(role);
	}
	
	@RequestMapping(value = "/{roleCode}", method = RequestMethod.DELETE)
	public Result<Role> remove(@PathVariable String roleCode) {
		Role role = roleRepository.findOneByCode(roleCode, DataState.NORMAL.code());
		roleRepository.save(role);
		return Result.success(role);
	}
	
	@RequestMapping(value = "/{roleCode}", method = RequestMethod.GET)
	public Result<Role> getOne(@PathVariable String roleCode) {
		Role role = roleRepository.findOneByCode(roleCode, DataState.NORMAL.code());
		return Result.success(role);
	}
}
