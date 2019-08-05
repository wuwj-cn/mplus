package com.mplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.advice.Result;
import com.mplus.entity.Role;
import com.mplus.service.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(@RequestBody Role role) {
		roleService.saveRole(role);
		return Result.sucess(role);	
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Result update(@RequestBody Role role) {
		roleService.updateRole(role);
		return Result.sucess(role);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result remove(@PathVariable String roleCode) {
		Role role = roleService.findOneByCode(roleCode);
		roleService.removeRole(role);
		return Result.sucess(role);
	}
	
	@RequestMapping(value = "/getOne/{roleCode}", method = RequestMethod.GET)
	public Result getOne(@PathVariable String roleCode) {
		Role role = roleService.findOneByCode(roleCode);
		return Result.sucess(role);
	}
}
