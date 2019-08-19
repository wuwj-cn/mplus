package com.mplus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MP_SYS_MENU")
public class Menu extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5001754947956101113L;

	@Column(length = 20, nullable = false, unique = true)
	private String menuCode;

	@Column(length = 100, nullable = false, unique = true)
	private String menuName;
	
	@Column(length = 100, nullable = false, unique = true)
	private String url;
	
	@Column(length = 20)
	private String icon;
	
	@Column(length=64)
	private String parentCode;

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
