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
import com.mplus.common.utils.jpa.JpaUtils;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.common.utils.jpa.QueryParam;
import com.mplus.common.utils.jpa.QueryType;
import com.mplus.common.vo.PageVo;
import com.mplus.system.entity.Org;
import com.mplus.system.entity.User;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.enums.UserStatus;
import com.mplus.system.repo.CodeRuleRepository;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.repo.UserRepository;
import com.mplus.system.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class UserController {

	private final UserRepository userRepository;
	private final OrgRepository orgRepository;
	private final CodeRuleRepository codeRuleRepository;

	@SneakyThrows
    @PostMapping(value = "/users")
	public Result<String> add(@RequestBody UserVo userVo) {
    	User user = new User();
        MBeanUtils.copyPropertiesIgnoreNull(userVo, user);

    	Org org = orgRepository.findOneByOrgCode(userVo.getOrgCode(), DataState.NORMAL.code());
    	user.setOrg(org);

		if (!StringUtils.isEmpty(user.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		if (StringUtils.isEmpty(user.getOrg().getOrgCode())) {
			throw new RuntimeException("org code is null");
		}
		String userId = codeRuleRepository.getSerial(RuleCode.USER);
		user.setUserId(userId);

		//对用户进行散列加密
//		String hashPassword = new BCryptPasswordEncoder().encode("123456");
//		user.setPassword(hashPassword);
		user.setPassword("123456");
		user.setUserStatus(UserStatus.ENABLE.code());
		userRepository.save(user);

		return Result.success(user.getUserId());
	}

	@SneakyThrows
	@PutMapping(value = "/users/{userId}")
	public Result<String> update(@RequestBody UserVo userVo, @PathVariable String userId) {
    	User user = userRepository.findByUserId(userId, DataState.NORMAL.code());
    	MBeanUtils.copyPropertiesIgnoreNull(userVo, user);
    	if(StringUtils.isNotBlank(userVo.getOrgCode())) {
    		Org org = orgRepository.findOneByOrgCode(userVo.getOrgCode(), DataState.NORMAL.code());
    		user.setOrg(org);
		} else {
    		throw new RuntimeException("org code is null");
		}
		userRepository.save(user);
		return Result.success(user.getUserId());
	}

	@SneakyThrows
	@DeleteMapping(value = "/users/{userId}")
	public Result<String> remove(@PathVariable String userId) {
		User user = userRepository.findByUserId(userId, DataState.NORMAL.code());
		user.setDataState(DataState.DELETED.code());
		userRepository.save(user);
		return Result.success(user.getUserId());
	}

	@SneakyThrows
	@GetMapping(value = "/users/{userId}")
	public Result<UserVo> getUserByUsername(@PathVariable String userId) {
		User user = userRepository.findByUserId(userId, DataState.NORMAL.code());
		UserVo userVo = new UserVo();
		MBeanUtils.copyPropertiesIgnoreNull(user, userVo);
		userVo.setOrgCode(user.getOrg().getOrgCode());
		userVo.setOrgName(user.getOrg().getOrgName());
		return Result.success(userVo);
	}

	@SneakyThrows
	@GetMapping(value = "/orgs/{orgCode}/users")
	public Result<List<UserVo>> listByOrg(@PathVariable String orgCode) {
		List<User> users = userRepository.findByOrg(orgCode, DataState.NORMAL.code());
		List<UserVo> userVos = new ArrayList<>();
		UserVo userVo = null;
		for (User user: users) {
			userVo = new UserVo();
			MBeanUtils.copyPropertiesIgnoreNull(user, userVo);
			userVo.setOrgCode(user.getOrg().getOrgCode());
			userVo.setOrgName(user.getOrg().getOrgName());
			userVos.add(userVo);
		}
		return Result.success(userVos);
	}

	@SneakyThrows
	@GetMapping(value = "/orgs/{orgCode}/users/page")
	public Result<PageVo<UserVo>> findPageUsersByOrg(
			@PathVariable String orgCode,
			@RequestParam int pageNumber,
			@RequestParam int pageSize,
			@RequestParam(required=false) String userName) {
		List<QueryParam> searchParams = new ArrayList<>();
		QueryParam param = null;
		if(StringUtils.isNotBlank(userName)) {
			param = QueryParam.build("userName", QueryType.like, userName);
			searchParams.add(param);
		}

		param = QueryParam.build("dataState", QueryType.eq, DataState.NORMAL.code());
		searchParams.add(param);
		
		List<String> properties = new ArrayList<>();
		properties.add("createTime"); //默认排序条件
		Direction direction = Direction.DESC; //默认倒序

		// pageable的页号是从0开始计算，因此根据传入的页号-1
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, new Sort(direction, properties));
		Specification<User> spec = JpaUtils.buildSpecification(searchParams);
		Page<User> users = userRepository.findAll(spec, pageable);

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
			userVo.setOrgCode(user.getOrg().getOrgCode());
			userVo.setOrgName(user.getOrg().getOrgName());
			userVos.add(userVo);
		}
		pageUsers.setContent(userVos);
		return Result.success(pageUsers);
	}
	
}
