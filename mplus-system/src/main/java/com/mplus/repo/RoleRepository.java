package com.mplus.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mplus.entity.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, String> {

	@Query(value = "from Role where dataStatus = ?2 and roleCode = ?1")
	Role findOneByCode(String roleCode, String dataStatus);
	
}
