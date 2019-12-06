package com.mplus.system.repo;

import java.util.List;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

	@Query(value = "select u from User u where u.userName = ?1 and u.dataState = ?2")
	User findByUserName(String userName, String dataStatus);
	
	@Query(value = "from User where dataState = ?2 and org.id = ?1")
	List<User> findByOrg(String orgId, String dataStatus);
	
	@Query(value = "from User where dataState = ?2 and userCode = ?1")
	User findOneByCode(String userCode, String dataStatus);
	
	@Query(value = "select u from User u left outer join fetch u.roles r "
			+ "where u.dataState = r.dataState and u.dataState = ?2 "
			+ "and r.id = ?1")
	List<User> findByRole(String roleId, String dataStatus);
}
