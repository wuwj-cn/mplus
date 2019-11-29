package com.mplus.system.repo;

import java.util.List;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wuwj
 *
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, String> {

	@Query(value = "from Menu where dataStatus = ?2 and menuCode = ?1")
	Menu findOneByCode(String menuCode, String dataStatus);
	
	@Query(value = "from Menu where dataStatus = ?2 and parentCode = ?1")
	List<Menu> findMenusByParent(String parentCode, String dataStatus);
	
}
