package com.mplus.service;

import com.mplus.entity.CodeRule;
import com.mplus.enums.RuleCode;

public interface CodeRuleService {

	CodeRule saveCodeRule(CodeRule rule);
	
	CodeRule updateCodeRule(CodeRule rule);
	
	void removeCodeRule(CodeRule rule);
	
	CodeRule findOneById(String ruleId);
	
	CodeRule findOneByCode(String ruleCode);
	
	String getSerial(RuleCode ruleCode);
}
