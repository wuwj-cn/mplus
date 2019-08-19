package com.mplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mplus.entity.Role;
import com.mplus.enums.RuleCode;
import com.mplus.enums.DataStatus;
import com.mplus.repo.RoleRepository;
import com.mplus.service.CodeRuleService;
import com.mplus.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private CodeRuleService codeRuleService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role saveRole(Role role) {
		if (!StringUtils.isEmpty(role.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		String roleCode = codeRuleService.getSerial(RuleCode.ROLE);
		role.setRoleCode(roleCode);
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
		role.setDataStatus(DataStatus.DELETED.getCode());
		roleRepository.save(role);
	}

	@Override
	public Role findOneByCode(String roleCode) {
		return roleRepository.findOneByCode(roleCode, DataStatus.NORMAL.getCode());
	}

}
