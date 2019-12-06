package com.mplus.system.service.impl;

import com.mplus.common.enums.DataStatus;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.entity.Role;
import com.mplus.system.repo.RoleRepository;
import com.mplus.system.service.CodeRuleService;
import com.mplus.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private CodeRuleService codeRuleService;
	private RoleRepository roleRepository;
	
	@Override
	public Role saveRole(Role role) {
		if (!StringUtils.isEmpty(role.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		String roleCode = codeRuleService.getSerial(RuleCode.ROLE);
		role.setRoleId(roleCode);
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
		role.setDataState(DataStatus.DELETED.getCode());
		roleRepository.save(role);
	}

	@Override
	public Role findOneByCode(String roleCode) {
		return roleRepository.findOneByCode(roleCode, DataStatus.NORMAL.getCode());
	}

}
