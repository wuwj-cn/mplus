package com.mplus.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;

import com.mplus.entity.CodeRule;

@Repository
public interface CodeRuleRepository extends BaseRepository<CodeRule, String> {

	@Query(value = "select o from CodeRule o where o.id = ?1")
	CodeRule findOneById(String id);

	@Query(value = "select o from CodeRule o where o.ruleCode = ?1 and o.dataStatus = ?2")
	CodeRule findOneByCode(String ruleCode, String dataStatus);
	
	@Async
	@Query(value = "select o from CodeRule o where o.ruleCode = ?1 and o.dataStatus = ?2")
	ListenableFuture<CodeRule> asyncFindOneByCode(String ruleCode, String dataStatus);
}
