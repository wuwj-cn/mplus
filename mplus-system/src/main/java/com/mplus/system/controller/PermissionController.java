package com.mplus.system.controller;

import com.mplus.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Permission;
import com.mplus.system.service.PermissionService;

@RestController
@RequestMapping(value = "/v1/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Permission> add(@RequestBody Permission permission) {
		permissionService.savePermission(permission);
		return Result.sucess(permission);	
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Result<Permission> update(@PathVariable String id, @RequestBody Permission permission) {
		if(!id.contentEquals(permission.getId()))
			throw new RuntimeException("update object is not equals");
		permissionService.updatePermission(permission);
		return Result.sucess(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.DELETE)
	public Result<Permission> remove(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		permissionService.removePermission(permission);
		return Result.sucess(permission);
	}
	
	@RequestMapping(value = "/{permissionCode}", method = RequestMethod.GET)
	public Result<Permission> getOne(@PathVariable String permissionCode) {
		Permission permission = permissionService.findOneByCode(permissionCode);
		return Result.sucess(permission);
	}
}
