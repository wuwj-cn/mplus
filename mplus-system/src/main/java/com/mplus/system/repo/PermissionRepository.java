package com.mplus.system.repo;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, String> {

	@Query(value = "from Permission where dataState = ?2 and permId = ?1")
	Permission findOneByCode(String permissionCode, String dataStatus);
}
