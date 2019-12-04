package com.mplus.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mplus.common.enums.DataStatus;
import com.mplus.common.repo.BaseRepository;
import com.mplus.common.service.impl.BaseServiceImpl;
import com.mplus.common.utils.tree.entity.CheckboxTreeNode;
import com.mplus.common.utils.tree.entity.TreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mplus.system.entity.Org;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.service.OrgService;

@Service
@Transactional
public class OrgServiceImpl extends BaseServiceImpl<Org, String> implements OrgService {

	@Autowired
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
		if(StringUtils.isEmpty(org.getParentOrgCode())) {
			org.setParentOrgCode("0");
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
		org.setDataStatus(DataStatus.DELETED.getCode());
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
				org -> nodes.add(new TreeNode(org.getId(), org.getOrgCode(), org.getOrgName(), false, false)));
		return nodes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreeNode> getCheckboxNodes(String id) {
		List<Org> orgs = this.findOrgsByParent(id);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		orgs.stream().forEach(org -> nodes
				.add(new CheckboxTreeNode(org.getId(), org.getOrgCode(), org.getOrgName(), false, false, false)));
		return nodes;
	}

}