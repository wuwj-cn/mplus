package com.mplus.system.service;

import com.mplus.system.entity.CodeRule;
import com.mplus.system.enums.RuleCode;

public interface CodeRuleService {

	CodeRule saveCodeRule(CodeRule rule);
	
	CodeRule updateCodeRule(CodeRule rule);
	
	void removeCodeRule(CodeRule rule);
	
	CodeRule findOneById(String ruleId);
	
	CodeRule findOneByCode(String ruleCode);
	
	String getSerial(RuleCode ruleCode);
}
