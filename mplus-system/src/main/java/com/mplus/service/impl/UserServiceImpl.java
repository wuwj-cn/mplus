package com.mplus.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mplus.entity.Role;
import com.mplus.entity.User;
import com.mplus.enums.DataStatus;
import com.mplus.enums.RuleCode;
import com.mplus.repo.BaseRepository;
import com.mplus.repo.UserRepository;
import com.mplus.service.CodeRuleService;
import com.mplus.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CodeRuleService codeRuleService;
	
	@Override
	public BaseRepository<User, String> getRepository() {
		return userRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName, DataStatus.NORMAL.getCode());
	}

	@Override
	public User saveUser(User user) {
		if (!StringUtils.isEmpty(user.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		if (StringUtils.isEmpty(user.getOrg().getId())) {
			throw new RuntimeException("org id is null");
		}
		String userCode = codeRuleService.getSerial(RuleCode.USER);
		user.setUserCode(userCode);
		
		//对用户进行散列加密
		String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(hashPassword);
		
		user.setUserStatus("0"); 
		super.save(user);
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOrgId(String orgId) {
		if (StringUtils.isEmpty(orgId)) {
			throw new RuntimeException("org is null");
		}
		return userRepository.findByOrg(orgId, DataStatus.NORMAL.getCode());
	}

	@Override
	public User findOneByCode(String userCode) {
		return userRepository.findOneByCode(userCode, DataStatus.NORMAL.getCode());
	}
	
	@Override
	public List<User> findByRole(Role role) {
		if (null == role) {
			throw new RuntimeException("role is null");
		}
		return userRepository.findByRole(role.getId(), DataStatus.NORMAL.getCode());
	}
}
