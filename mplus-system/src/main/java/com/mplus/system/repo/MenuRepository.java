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

	@Query(value = "from Menu where dataState = ?2 and menuId = ?1")
	Menu findOneByCode(String menuCode, String dataStatus);
	
	@Query(value = "from Menu where dataState = ?2 and parentMenuId = ?1")
	List<Menu> findMenusByParent(String parentCode, String dataStatus);
	
}
