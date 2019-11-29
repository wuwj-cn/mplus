package com.mplus.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.mplus.common.entity.BaseEntity;
import com.mplus.common.enums.RulePolicy;
import com.mplus.common.enums.RulePolicyConverter;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "MP_SYS_CODE_RULE")
public class CodeRule extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4498917973674072910L;
	
	@Column(length=20, nullable = false)
	private String ruleCode;
	
	@Column(length=100, nullable = false)
	private String ruleName;
	
	@Column(length=100)
	private String rulePrefix;
	
	@Column(length=3, nullable = false)
	@Convert(converter = RulePolicyConverter.class)
	private RulePolicy rulePolicy;
	
	@Column(length=5)
	private Integer serialLength;
	
	@Column(length=13)
	private String currentValue;
	
	@Column(length=255)
	private String remark;
	
	public CodeRule() {}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRulePrefix() {
		return rulePrefix;
	}

	public void setRulePrefix(String rulePrefix) {
		this.rulePrefix = rulePrefix;
	}

	@JSONField(serialize = false)
	public RulePolicy getRulePolicy() {
		return rulePolicy;
	}

	@JSONField(serialize = false)
	public void setRulePolicy(RulePolicy rulePolicy) {
		this.rulePolicy = rulePolicy;
	}
	
	@JSONField(name = "policy")
	public String getPolicy() {
		return rulePolicy.getCode();
	}
	
	@JSONField(name = "policy")
	public void setPolicy(String code) {
		this.rulePolicy = RulePolicy.fromString(code);
	}

	public Integer getSerialLength() {
		return serialLength;
	}

	public void setSerialLength(Integer serialLength) {
		this.serialLength = serialLength;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
