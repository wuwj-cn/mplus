package com.mplus.system.repo;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

	@Query(value = "from Role where dataStatus = ?2 and roleCode = ?1")
	Role findOneByCode(String roleCode, String dataStatus);
	
}
