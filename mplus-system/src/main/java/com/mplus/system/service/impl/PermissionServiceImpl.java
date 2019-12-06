package com.mplus.system.service.impl;

import com.mplus.common.enums.DataStatus;
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
		permission.setDataState(DataStatus.DELETED.getCode());
		permissionRepository.save(permission);
	}

	@Override
	public Permission findOneByCode(String priviCode) {
		return permissionRepository.findOneByCode(priviCode, DataStatus.NORMAL.getCode());
	}

}
