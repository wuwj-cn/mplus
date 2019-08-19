package com.mplus.service.impl;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mplus.entity.CodeRule;
import com.mplus.enums.RuleCode;
import com.mplus.enums.DataStatus;
import com.mplus.repo.CodeRuleRepository;
import com.mplus.service.CodeRuleService;
import com.mplus.utils.RuleUtil;

@Service
@Transactional
public class CodeRuleServiceImpl implements CodeRuleService {

	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();;

	@Autowired
	CodeRuleRepository codeRuleRespository;

	@Override
	public CodeRule saveCodeRule(CodeRule rule) {
		CodeRule r = codeRuleRespository.findOneByCode(rule.getRuleCode(), DataStatus.NORMAL.getCode());
		if (r != null) {
			throw new RuntimeException("规则编码已存在");
		}
		codeRuleRespository.save(rule);
		return rule;
	}

	@Override
	public CodeRule updateCodeRule(CodeRule rule) {
		if (StringUtils.isEmpty(rule.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		codeRuleRespository.save(rule);
		return rule;
	}

	@Override
	public void removeCodeRule(CodeRule rule) {
		rule.setDataStatus(DataStatus.DELETED.getCode());
		codeRuleRespository.save(rule);
	}

	@Override
	public CodeRule findOneById(String ruleId) {
		return codeRuleRespository.findOneById(ruleId);
	}

	@Override
	public CodeRule findOneByCode(String ruleCode) {
		return codeRuleRespository.findOneByCode(ruleCode, DataStatus.NORMAL.getCode());
	}

	/**
	 * 高并发流水号生成
	 */
	public String getSerial(RuleCode ruleCode) {
		String serial = null;
		try {
			lock.readLock().lock();
			CodeRule rule = codeRuleRespository.findOneByCode(ruleCode.getCode(), DataStatus.NORMAL.getCode());
			if(null == rule) {
				throw new RuntimeException("not set rule code");
			}
			try {
				lock.readLock().unlock();
				lock.writeLock().lock();
				serial = RuleUtil.getSerial(rule);
				rule.setCurrentValue(serial);
				codeRuleRespository.save(rule);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.readLock().lock();
				lock.writeLock().unlock();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		return serial;
	}
}
