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

/**
 * 
 */
package com.mplus.system.repo;

import com.mplus.common.enums.DataState;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.utils.tree.entity.CheckboxTreeNode;
import com.mplus.common.utils.tree.entity.TreeNode;
import com.mplus.system.entity.Org;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuwj
 *
 */
@Repository
public interface OrgRepository extends BaseRepository<Org, String> {

	@Query(value = "from Org where dataState = ?2 and orgId = ?1")
	Org findOneByOrgId(String orgId, String dataStatus);

	@Query(value = "from Org where dataState = ?2 and parentOrg.orgId = ?1")
	List<Org> findOrgsByParent(String parentOrgId, String dataStatus);

	@Query(value = "from Org where dataState = ?1")
	List<Org> findAll(String dataStatus);

	default List<TreeNode> getNodes(String orgId, Boolean checkbox) {
		Org parentOrg = new Org();
		parentOrg.setOrgId(orgId);
		Org org = new Org();
		org.setParentOrg(parentOrg);
		org.setDataState(DataState.NORMAL.code());
		List<Org> orgs = this.findAll(Example.of(org));
		List<TreeNode> nodes = new ArrayList<>();
		if(checkbox) {
			orgs.stream().forEach(_org -> nodes
					.add(new CheckboxTreeNode(_org.getId(), _org.getOrgId(), _org.getOrgName(), false, false, false)));
		} else {
			orgs.stream().forEach(
					_org -> nodes.add(new TreeNode(_org.getId(), _org.getOrgId(), _org.getOrgName(), false, false)));
		}
		return nodes;
	}
}
