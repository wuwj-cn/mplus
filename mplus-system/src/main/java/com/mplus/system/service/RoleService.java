package com.mplus.system.service;

import com.mplus.system.entity.Role;

public interface RoleService {

	Role saveRole(Role role);
	
	Role updateRole(Role role);
	
	void removeRole(Role role);
	
	Role findOneByCode(String roleCode);
}
