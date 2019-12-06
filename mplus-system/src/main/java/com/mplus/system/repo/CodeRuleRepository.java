package com.mplus.system.repo;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.CodeRule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;

@Repository
public interface CodeRuleRepository extends BaseRepository<CodeRule, String> {

	@Query(value = "select o from CodeRule o where o.id = ?1")
	CodeRule findOneById(String id);

	@Query(value = "select o from CodeRule o where o.ruleId = ?1 and o.dataState = ?2")
	CodeRule findOneByCode(String ruleCode, String dataStatus);
	
	@Async
	@Query(value = "select o from CodeRule o where o.ruleId = ?1 and o.dataState = ?2")
	ListenableFuture<CodeRule> asyncFindOneByCode(String ruleCode, String dataStatus);
}
