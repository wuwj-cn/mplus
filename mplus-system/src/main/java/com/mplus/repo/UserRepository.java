package com.mplus.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mplus.entity.User;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

	@Query(value = "select u from User u where u.userName = ?1 and u.dataStatus = ?2")
	User findByUserName(String userName, String dataStatus);
	
	@Query(value = "from User where dataStatus = ?2 and org.id = ?1")
	List<User> findByOrg(String orgId, String dataStatus);
	
	@Query(value = "from User where dataStatus = ?2 and userCode = ?1")
	User findOneByCode(String userCode, String dataStatus);
	
	@Query(value = "select u from User u left outer join fetch u.roles r "
			+ "where u.dataStatus = r.dataStatus and u.dataStatus = ?2 "
			+ "and r.id = ?1")
	List<User> findByRole(String roleId, String dataStatus);
}
