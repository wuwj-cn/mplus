/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package com.mplus.system.repo;

import java.util.List;

import com.mplus.common.repo.BaseRepository;
import com.mplus.system.entity.Org;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wuwj
 *
 */
@Repository
public interface OrgRepository extends BaseRepository<Org, String> {

	@Query(value = "from Org where dataState = ?2 and orgId = ?1")
	Org findOneByOrgId(String orgId, String dataStatus);
	
	@Query(value = "from Org where dataState = ?2 and parentOrgId = ?1")
	List<Org> findOrgsByParent(String parentOrgCode, String dataStatus);
	
	@Query(value = "from Org where dataState = ?1")
	List<Org> findAll(String dataStatus);
}
