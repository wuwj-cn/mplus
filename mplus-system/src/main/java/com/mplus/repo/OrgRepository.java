/**
 * 
 */
package com.mplus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mplus.entity.Org;

/**
 * @author wuwj
 *
 */
@Repository
public interface OrgRepository extends BaseRepository<Org, String> {

	@Query(value = "from Org where dataStatus = ?2 and orgCode = ?1")
	Org findOneByCode(String orgCode, String dataStatus);
	
	@Query(value = "from Org where dataStatus = ?2 and parentOrgCode = ?1")
	List<Org> findOrgsByParent(String parentOrgCode, String dataStatus);
	
	@Query(value = "from Org where dataStatus = ?1")
	List<Org> findAll(String dataStatus);
}
