package com.mplus.system.service;

import java.util.List;

import com.mplus.system.entity.Role;
import com.mplus.system.entity.User;
import com.mplus.common.service.BaseService;

public interface UserService extends BaseService<User, String> {

	User saveUser(User user);
	
	User findByUsername(String username);
	
	List<User> findByOrgId(String orgId);
	
	User findOneByCode(String userCode);
	
	List<User> findByRole(Role role);
}
