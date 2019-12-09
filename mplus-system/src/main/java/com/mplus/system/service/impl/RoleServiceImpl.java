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

package com.mplus.system.service.impl;

import com.mplus.common.enums.DataState;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.entity.Role;
import com.mplus.system.repo.CodeRuleRepository;
import com.mplus.system.repo.RoleRepository;
import com.mplus.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private CodeRuleRepository codeRuleRepository;
	private RoleRepository roleRepository;
	
	@Override
	public Role saveRole(Role role) {
		if (!StringUtils.isEmpty(role.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		String roleCode = codeRuleRepository.getSerial(RuleCode.ROLE);
		role.setRoleId(roleCode);
		return roleRepository.save(role);
	}

	@Override
	public Role updateRole(Role role) {
		if (StringUtils.isEmpty(role.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		return roleRepository.save(role);
	}

	@Override
	public void removeRole(Role role) {
		role.setUsers(null); // remove all user
		role.setPermissions(null); // remove all permissions
		role.setDataState(DataState.DELETED.code());
		roleRepository.save(role);
	}

	@Override
	public Role findOneByCode(String roleCode) {
		return roleRepository.findOneByCode(roleCode, DataState.NORMAL.code());
	}

}
