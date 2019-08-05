package com.mplus.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mplus.entity.Permission;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, String> {

	@Query(value = "from Permission where status = ?2 and permissionCode = ?1")
	Permission findOneByCode(String permissionCode, String status);
}
