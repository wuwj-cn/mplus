package com.mplus.system.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.mplus.common.entity.BaseEntity;
import com.mplus.common.enums.PermissionType;
import com.mplus.common.enums.PermissionTypeConverter;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "MP_SYS_PERMISSION")
public class Permission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -7748357850803156457L;
	
	@Column(length=20, nullable = false, unique = true)
	private String permissionCode;
	
	@Column(length=100, nullable = false)
	private String permissionName;
	
	@Column(length = 2, nullable = false)
	@Convert(converter = PermissionTypeConverter.class)
	private PermissionType permissionType;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MP_SYS_ROLE_PERMI_REL", joinColumns = {
			@JoinColumn(name = "PERMISSION_ID")
	}, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID")
	})
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

	@JSONField(serialize = false)
	public PermissionType getPermissionType() {
		return permissionType;
	}

	@JSONField(serialize = false)
	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}
	
	@JSONField(name = "ptype")
	public String getPtype() {
		return permissionType.getCode();
	}
	
	@JSONField(name = "ptype")
	public void setPtype(String code) {
		this.permissionType = PermissionType.fromString(code);
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
