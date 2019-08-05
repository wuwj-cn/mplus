package com.mplus.service;

import com.mplus.entity.Role;

public interface RoleService {

	Role saveRole(Role role);
	
	Role updateRole(Role role);
	
	void removeRole(Role role);
	
	Role findOneByCode(String roleCode);
}
