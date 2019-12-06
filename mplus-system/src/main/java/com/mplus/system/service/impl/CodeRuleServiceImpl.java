package com.mplus.system.service.impl;

import com.mplus.common.enums.DataStatus;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.entity.CodeRule;
import com.mplus.system.repo.CodeRuleRepository;
import com.mplus.system.service.CodeRuleService;
import com.mplus.system.util.RuleUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@Transactional
@AllArgsConstructor
public class CodeRuleServiceImpl implements CodeRuleService {

	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();;

	CodeRuleRepository codeRuleRepository;

	@Override
	public CodeRule saveCodeRule(CodeRule rule) {
		CodeRule r = codeRuleRepository.findOneByCode(rule.getRuleId(), DataStatus.NORMAL.getCode());
		if (r != null) {
			throw new RuntimeException("规则编码已存在");
		}
		codeRuleRepository.save(rule);
		return rule;
	}

	@Override
	public CodeRule updateCodeRule(CodeRule rule) {
		if (StringUtils.isEmpty(rule.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		codeRuleRepository.save(rule);
		return rule;
	}

	@Override
	public void removeCodeRule(CodeRule rule) {
		rule.setDataState(DataStatus.DELETED.getCode());
		codeRuleRepository.save(rule);
	}

	@Override
	public CodeRule findOneById(String ruleId) {
		return codeRuleRepository.findOneById(ruleId);
	}

	@Override
	public CodeRule findOneByCode(String ruleCode) {
		return codeRuleRepository.findOneByCode(ruleCode, DataStatus.NORMAL.getCode());
	}

	/**
	 * 高并发流水号生成
	 */
	public String getSerial(RuleCode ruleCode) {
		String serial = null;
		try {
			lock.readLock().lock();
			CodeRule rule = codeRuleRepository.findOneByCode(ruleCode.getCode(), DataStatus.NORMAL.getCode());
			if(null == rule) {
				throw new RuntimeException("not set rule code");
			}
			try {
				lock.readLock().unlock();
				lock.writeLock().lock();
				serial = RuleUtil.getSerial(rule);
				rule.setCurrentValue(serial);
				codeRuleRepository.save(rule);
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
