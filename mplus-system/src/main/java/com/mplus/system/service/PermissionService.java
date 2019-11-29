package com.mplus.system.service;

import com.mplus.system.entity.Permission;

public interface PermissionService {

	Permission savePermission(Permission permission);
	
	Permission updatePermission(Permission permission);
	
	void removePermission(Permission permission);
	
	Permission findOneByCode(String permissionCode);
}
