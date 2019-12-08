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

package com.mplus.system.service.impl;

import com.mplus.common.enums.DataState;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.impl.BaseServiceImpl;
import com.mplus.system.entity.Role;
import com.mplus.system.entity.User;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.enums.UserStatus;
import com.mplus.system.repo.UserRepository;
import com.mplus.system.service.CodeRuleService;
import com.mplus.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	private UserRepository userRepository;
	private CodeRuleService codeRuleService;
	
	@Override
	public BaseRepository<User, String> getRepository() {
		return userRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName, DataState.NORMAL.getCode());
	}

	@Override
	@SneakyThrows
	public User saveUser(User user) {
		if (!StringUtils.isEmpty(user.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		if (StringUtils.isEmpty(user.getOrg().getOrgId())) {
			throw new RuntimeException("org id is null");
		}
		String userId = codeRuleService.getSerial(RuleCode.USER);
		user.setUserId(userId);
		
		//对用户进行散列加密
//		String hashPassword = new BCryptPasswordEncoder().encode("123456");
//		user.setPassword(hashPassword);
		user.setPassword("123456");
		user.setUserStatus(UserStatus.ENABLE.code());
		super.save(user);
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOrgId(String orgId) {
		if (StringUtils.isEmpty(orgId)) {
			throw new RuntimeException("org is null");
		}
		return userRepository.findByOrg(orgId, DataState.NORMAL.getCode());
	}

	@Override
	public User findOneByCode(String userCode) {
		return userRepository.findOneByCode(userCode, DataState.NORMAL.getCode());
	}
	
	@Override
	public List<User> findByRole(Role role) {
		if (null == role) {
			throw new RuntimeException("role is null");
		}
		return userRepository.findByRole(role.getId(), DataState.NORMAL.getCode());
	}
}
