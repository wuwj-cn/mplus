package com.mplus.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MP_SYS_ROLE")
public class Role extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6520776568058298929L;
	
	@Column(length=20, nullable = false, unique = true)
	private String roleCode;
	
	@Column(length=100, nullable = false)
	private String roleName;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MP_SYS_USER_ROLE_REL", joinColumns = {
			@JoinColumn(name = "ROLE_ID")
	}, inverseJoinColumns = {
			@JoinColumn(name = "USER_ID")
	})
	private Set<User> users = new HashSet<User>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MP_SYS_ROLE_PERMI_REL", joinColumns = {
			@JoinColumn(name = "ROLE_ID")
	}, inverseJoinColumns = {
			@JoinColumn(name = "PERMI_ID")
	})
	private Set<Permission> permissions = new HashSet<Permission>();
	
	public Role() {
	}

	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
