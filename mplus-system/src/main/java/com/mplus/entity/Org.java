/**
 * 
 */
package com.mplus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wuwj
 *
 */
@Entity
@Table(name = "MP_SYS_ORG")
public class Org extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5221776501169665796L;
	
	@Column(length=20, nullable = false, unique = true)
	private String orgCode;
	
	@Column(length=100, nullable = false)
	private String orgName;
	
	@Column(length=64)
	private String parentId;
	
	@Column(length=255)
	private String remark;
	
	public Org(){}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
