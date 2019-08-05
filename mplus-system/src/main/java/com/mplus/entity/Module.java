package com.mplus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "MP_SYS_MODULE")
public class Module extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 9101244152735169725L;

	@Column(length=20, nullable = false, unique = true)
	private String moduleCode;
	
	@Column(length=20, nullable = false)
	private String moduleName;
	
	@Column(length=100)
	private String moduleDesc;
	
	@Column(length=10)
	private String version;
	
	@Column(length=100)
	private String upgradeInfo;

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpgradeInfo() {
		return upgradeInfo;
	}

	public void setUpgradeInfo(String upgradeInfo) {
		this.upgradeInfo = upgradeInfo;
	}
	
}
