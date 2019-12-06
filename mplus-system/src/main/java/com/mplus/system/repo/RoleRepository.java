package com.mplus.system.repo;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

	@Query(value = "from Role where dataState = ?2 and roleId = ?1")
	Role findOneByCode(String roleCode, String dataStatus);
	
}
