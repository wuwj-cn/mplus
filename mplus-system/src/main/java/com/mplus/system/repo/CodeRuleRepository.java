/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.system.repo;

import com.mplus.common.enums.DataState;
import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.CodeRule;
import com.mplus.system.enums.RuleCode;
import com.mplus.system.util.RuleUtil;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public interface CodeRuleRepository extends BaseRepository<CodeRule, String> {

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();;

	@Query(value = "select o from CodeRule o where o.id = ?1")
	CodeRule findOneById(String id);

	@Query(value = "select o from CodeRule o where o.ruleId = ?1 and o.dataState = ?2")
	CodeRule findOneByCode(String ruleCode, String dataStatus);
	
	@Async
	@Query(value = "select o from CodeRule o where o.ruleId = ?1 and o.dataState = ?2")
	ListenableFuture<CodeRule> asyncFindOneByCode(String ruleCode, String dataStatus);

	/**
	 * 高并发流水号生成
	 */
	default String getSerial(RuleCode ruleCode) {
		String serial = null;
		try {
			lock.readLock().lock();
			CodeRule rule = this.findOneByCode(ruleCode.getCode(), DataState.NORMAL.code());
			if(null == rule) {
				throw new RuntimeException("not set rule code");
			}
			try {
				lock.readLock().unlock();
				lock.writeLock().lock();
				serial = RuleUtil.getSerial(rule);
				rule.setCurrentValue(serial);
				this.save(rule);
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
