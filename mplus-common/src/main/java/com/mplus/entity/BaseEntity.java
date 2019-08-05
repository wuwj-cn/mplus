package com.mplus.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.mplus.enums.Status;

@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
    @Column(length=64)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(length=64)
	private String createBy; // 插入人

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createDate; // 插入时间

	@Column(length=64)
	private String updateBy; // 修改人

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate; // 修改时间

	@Column(length=2, nullable = false)
	private String status; // 状态, 0正常 1删除 2停用

	public BaseEntity() {
		this.createDate = this.updateDate = new Date();
		this.status = Status.NORMAL.getCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
