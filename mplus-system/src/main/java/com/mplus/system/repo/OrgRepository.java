/**
 * 
 */
package com.mplus.system.repo;

import java.util.List;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Org;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wuwj
 *
 */
@Repository
public interface OrgRepository extends BaseRepository<Org, String> {

	@Query(value = "from Org where dataState = ?2 and orgId = ?1")
	Org findOneByCode(String orgCode, String dataStatus);
	
	@Query(value = "from Org where dataState = ?2 and parentOrgId = ?1")
	List<Org> findOrgsByParent(String parentOrgCode, String dataStatus);
	
	@Query(value = "from Org where dataState = ?1")
	List<Org> findAll(String dataStatus);
}
