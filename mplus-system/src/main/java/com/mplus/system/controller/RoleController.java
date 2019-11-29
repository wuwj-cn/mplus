package com.mplus.system.controller;

import com.mplus.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Role;
import com.mplus.system.service.RoleService;

@RestController
@RequestMapping(value = "/v1/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Role> add(@RequestBody Role role) {
		roleService.saveRole(role);
		return Result.sucess(role);	
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Role> update(@PathVariable String id, @RequestBody Role role) {
		if(!id.contentEquals(role.getId()))
			throw new RuntimeException("update object is not equals");
		roleService.updateRole(role);
		return Result.sucess(role);
	}
	
	@RequestMapping(value = "/{roleCode}", method = RequestMethod.DELETE)
	public Result<Role> remove(@PathVariable String roleCode) {
		Role role = roleService.findOneByCode(roleCode);
		roleService.removeRole(role);
		return Result.sucess(role);
	}
	
	@RequestMapping(value = "/{roleCode}", method = RequestMethod.GET)
	public Result<Role> getOne(@PathVariable String roleCode) {
		Role role = roleService.findOneByCode(roleCode);
		return Result.sucess(role);
	}
}
