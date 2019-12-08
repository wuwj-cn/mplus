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

import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.system.entity.Org;
import com.mplus.system.entity.User;
import com.mplus.system.service.OrgService;
import com.mplus.system.service.UserService;
import com.mplus.common.vo.PageVo;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final OrgService orgService;

	@SneakyThrows
    @PostMapping(value = "/users")
	public Result<String> add(@RequestBody UserVo userVo) {
    	User user = new User();
        MBeanUtils.copyPropertiesIgnoreNull(userVo, user);

    	Org org = orgService.findOneByOrgId(userVo.getOrgId());
    	user.setOrg(org);
		userService.saveUser(user);
		return Result.success(user.getUserId());
	}

	@SneakyThrows
	@PutMapping(value = "/users/{userName}")
	public Result<User> update(@RequestBody UserVo userVo, @PathVariable String userName) {
    	User user = userService.findByUsername(userName);
    	MBeanUtils.copyPropertiesIgnoreNull(userVo, user);
    	if(StringUtils.isNotBlank(userVo.getOrgId())) {
    		Org org = orgService.findOneByOrgId(userVo.getOrgId());
    		user.setOrg(org);
		} else {
    		throw new RuntimeException("org id is null");
		}
		userService.update(user);
		return Result.success(null);
	}

	@SneakyThrows
	@DeleteMapping(value = "/users/{userName}")
	public Result<User> remove(@PathVariable String userName) {
		User user = userService.findByUsername(userName);
		userService.delete(user);
		return Result.success(null);
	}

	@SneakyThrows
	@GetMapping(value = "/users/{userName}")
	public Result<UserVo> getUserByUsername(@PathVariable String userName) {
		User user = userService.findByUsername(userName);
		UserVo userVo = new UserVo();
		MBeanUtils.copyPropertiesIgnoreNull(user, userVo);
		userVo.setOrgId(user.getOrg().getOrgId());
		userVo.setOrgName(user.getOrg().getOrgName());
		return Result.success(userVo);
	}

	@SneakyThrows
	@GetMapping(value = "/orgs/{orgId}/users")
	public Result<List<UserVo>> listByOrg(@PathVariable String orgId) {
		List<User> users = userService.findByOrgId(orgId);
		List<UserVo> userVos = new ArrayList<>();
		UserVo userVo = null;
		for (User user: users) {
			userVo = new UserVo();
			MBeanUtils.copyPropertiesIgnoreNull(user, userVo);
			userVo.setOrgId(user.getOrg().getOrgId());
			userVo.setOrgName(user.getOrg().getOrgName());
			userVos.add(userVo);
		}
		return Result.success(userVos);
	}

	@SneakyThrows
	@GetMapping(value = "/orgs/{orgId}/users/page")
	public Result<PageVo<UserVo>> findPageUsersByOrg(
			@RequestParam int pageNumber,
			@RequestParam int pageSize,
			@RequestParam(required=false) String userName) {
		Map<String, Object> searchParams = new HashMap<>();
		if(StringUtils.isNotBlank(userName)) searchParams.put("userName:like", userName);
		
		List<String> properties = new ArrayList<>();
		properties.add("createTime"); //默认排序条件
		Direction direction = Direction.DESC; //默认倒序

		// pageable的页号是从0开始计算，因此根据传入的页号-1
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, new Sort(direction, properties));
		Page<User> users = userService.findPage(searchParams, pageable);
		PageVo<UserVo> pageUsers = new PageVo<>();
		pageUsers.setTotalElements(users.getTotalElements());
		pageUsers.setTotalPages(users.getTotalPages());
		pageUsers.setPageNumber(pageNumber);
		pageUsers.setPageSize(pageSize);
		List<UserVo> userVos = new ArrayList<>();
		UserVo userVo = null;
		for(User user : users.getContent()) {
			userVo = new UserVo();
			MBeanUtils.copyPropertiesIgnoreNull(user, userVo);
			userVo.setOrgId(user.getOrg().getOrgId());
			userVo.setOrgName(user.getOrg().getOrgName());
			userVos.add(userVo);
		}
		pageUsers.setContent(userVos);
		return Result.success(pageUsers);
	}
	
}
