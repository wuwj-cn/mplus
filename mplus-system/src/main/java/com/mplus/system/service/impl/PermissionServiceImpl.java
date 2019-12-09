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
import com.mplus.system.entity.Permission;
import com.mplus.system.repo.PermissionRepository;
import com.mplus.system.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

	private PermissionRepository permissionRepository;
	
	@Override
	public Permission savePermission(Permission permission) {
		if (!StringUtils.isEmpty(permission.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		return permissionRepository.save(permission);
	}

	@Override
	public Permission updatePermission(Permission permission) {
		if(StringUtils.isEmpty(permission.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		return permissionRepository.save(permission);
	}

	@Override
	public void removePermission(Permission permission) {
		permission.setRoles(null);
		permission.setDataState(DataState.DELETED.code());
		permissionRepository.save(permission);
	}

	@Override
	public Permission findOneByCode(String priviCode) {
		return permissionRepository.findOneByCode(priviCode, DataState.NORMAL.code());
	}

}
