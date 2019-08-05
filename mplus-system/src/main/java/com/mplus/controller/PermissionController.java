package com.mplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.advice.Result;
import com.mplus.entity.Permission;
import com.mplus.service.PermissionService;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(@RequestBody Permission permission) {
		permissionService.savePermission(permission);
		return Result.sucess(permission);	
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Result update(@RequestBody Permission permission) {
		permissionService.updatePermission(permission);
		return Result.sucess(permission);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result remove(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		permissionService.removePermission(permission);
		return Result.sucess(permission);
	}
	
	@RequestMapping(value = "/getOne/{permissionCode}", method = RequestMethod.GET)
	public Result getOne(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		return Result.sucess(permission);
	}
}
