package com.mplus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mplus.entity.Menu;

/**
 * @author wuwj
 *
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, String> {

	@Query(value = "from Menu where dataStatus = ?2 and menuCode = ?1")
	Menu findOneByCode(String menuCode, String dataStatus);
	
	@Query(value = "from Menu where dataStatus = ?2 and parentId = ?1")
	List<Menu> findMenusByParent(String parentId, String dataStatus);
	
}
