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
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mplus.system.service.impl;

import com.mplus.common.enums.DataStatus;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.impl.BaseServiceImpl;
import com.mplus.common.utils.tree.entity.CheckboxTreeNode;
import com.mplus.common.utils.tree.entity.TreeNode;
import com.mplus.system.entity.Org;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.service.OrgService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OrgServiceImpl extends BaseServiceImpl<Org, String> implements OrgService {

	private OrgRepository orgRepository;
	
	@Override
	public BaseRepository<Org, String> getRepository() {
		return orgRepository;
	}

	@Override
	public Org saveOrg(Org org) {
		if (!StringUtils.isEmpty(org.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		if(StringUtils.isEmpty(org.getParentOrgId())) {
			org.setParentOrgId("0");
		}
		return orgRepository.save(org);
	}

	@Override
	public Org updateOrg(Org org) {
		if (StringUtils.isEmpty(org.getId())) {
			throw new RuntimeException("object id is null or empty");
		}
		return orgRepository.save(org);
	}

	@Override
	public void removeOrg(Org org) {
		org.setDataState(DataStatus.DELETED.getCode());
		orgRepository.save(org);
	}

	@Override
	@Transactional(readOnly = true)
	public Org findOneByCode(String orgCode) {
		return orgRepository.findOneByCode(orgCode, DataStatus.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Org> findOrgsByParent(String parentOrgCode) {
		return orgRepository.findOrgsByParent(parentOrgCode, DataStatus.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreeNode> getNodes(String orgCode) {
		List<Org> orgs = this.findOrgsByParent(orgCode);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		orgs.stream().forEach(
				org -> nodes.add(new TreeNode(org.getId(), org.getOrgId(), org.getOrgName(), false, false)));
		return nodes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreeNode> getCheckboxNodes(String id) {
		List<Org> orgs = this.findOrgsByParent(id);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		orgs.stream().forEach(org -> nodes
				.add(new CheckboxTreeNode(org.getId(), org.getOrgId(), org.getOrgName(), false, false, false)));
		return nodes;
	}

}
