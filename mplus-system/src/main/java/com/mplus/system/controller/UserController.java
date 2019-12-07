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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.system.entity.Org;
import com.mplus.system.service.OrgService;
import com.mplus.system.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import com.mplus.system.entity.User;
import com.mplus.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final OrgService orgService;

	private final HttpServletRequest request;
	private final HttpServletResponse response;

    @PostMapping(value = "/users")
	@SneakyThrows
	public Result<String> add(@RequestBody UserVo userVo) {
    	User user = new User();
        MBeanUtils.copyPropertiesIgnoreNull(userVo, user);

    	Org org = orgService.findOneByCode(userVo.getOrgId());
    	user.setOrg(org);
		userService.saveUser(user);
		return Result.success(user.getUserId());
	}
	
	@PutMapping(value = "/users/{userId}")
	public Result<User> update(@RequestBody User user, @PathVariable String userId) {
		userService.update(user);
		return Result.success(user);
	}
	
	@DeleteMapping(value = "/users/{userId")
	public Result<User> remove(@PathVariable String userId) {
		User user = userService.findOneByCode(userId);
		userService.delete(user);
		return Result.success(user);
	}
	
	@GetMapping(value = "/users/{userName}")
	public Result<User> getUserByUsername(@PathVariable String userName) {
		User user = userService.findByUsername(userName);
		return Result.success(user);
	}
	
	@GetMapping(value = "/orgs/{orgId}/users")
	public Result<List<User>> listByOrg(@PathVariable String orgId) {
		List<User> users = userService.findByOrgId(orgId);
		return Result.success(users);
	}
	
	@GetMapping(value = "/orgs/{orgId}/users/page")
	public Result<Page<User>> list(
			@RequestParam int pi, 
			@RequestParam int ps, 
			@RequestParam(required=false) String username, 
			@RequestParam(required=false) String nickName, 
			@RequestParam(required=false) String status,
			@RequestParam(required=false) String sortProperties,
			@RequestParam(required=false) String sortDirection) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(username)) searchParams.put("username:like", username);
		if(StringUtils.isNotBlank(nickName)) searchParams.put("nickName:like", nickName);
		
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
		
		Pageable pageable = PageRequest.of(pi, ps, new Sort(direction, properties));
		Page<User> users = userService.findPage(searchParams, pageable);
		return Result.success(users);
	}
	
}
