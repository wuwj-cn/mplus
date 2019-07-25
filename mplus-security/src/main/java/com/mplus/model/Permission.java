package com.mplus.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Permission implements Serializable {
	private static final long serialVersionUID = -7748357850803156457L;
	
	private String permissionCode;
	
	private String permissionName;
	
	private Set<Role> roles = new HashSet<Role>();
	
	public Permission() {}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
