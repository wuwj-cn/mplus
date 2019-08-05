package com.mplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mplus.entity.Permission;
import com.mplus.enums.Status;
import com.mplus.repo.PermissionRepository;
import com.mplus.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository privilegeRepository;
	
	@Override
	public Permission savePermission(Permission permission) {
		if (!StringUtils.isEmpty(permission.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		return privilegeRepository.save(permission);
	}

	@Override
	public Permission updatePermission(Permission permission) {
		if(StringUtils.isEmpty(permission.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		return privilegeRepository.save(permission);
	}

	@Override
	public void removePermission(Permission permission) {
		permission.setRoles(null);
		permission.setStatus(Status.DELETED.getCode());
		privilegeRepository.save(permission);
	}

	@Override
	public Permission findOneByCode(String priviCode) {
		return privilegeRepository.findOneByCode(priviCode, Status.NORMAL.getCode());
	}

}
