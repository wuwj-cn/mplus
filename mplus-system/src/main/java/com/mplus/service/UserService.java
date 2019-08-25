package com.mplus.service;

import java.util.List;

import com.mplus.entity.Role;
import com.mplus.entity.User;

public interface UserService extends BaseService<User, String> {

	User saveUser(User user);
	
	User findByUsername(String username);
	
	List<User> findByOrgId(String orgId);
	
	User findOneByCode(String userCode);
	
	List<User> findByRole(Role role);
}
