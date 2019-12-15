/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	
	@Query(value = "from User where dataState = ?2 and org.orgCode = ?1")
	List<User> findByOrg(String orgCode, String dataStatus);
	
	@Query(value = "from User where dataState = ?2 and userId = ?1")
	User findByUserId(String userId, String dataStatus);
	
	@Query(value = "select u from User u left outer join fetch u.roles r "
			+ "where u.dataState = r.dataState and u.dataState = ?2 "
			+ "and r.id = ?1")
	List<User> findByRole(String roleId, String dataStatus);
}
