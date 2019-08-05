package com.mplus.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mplus.entity.Org;
import com.mplus.enums.Status;
import com.mplus.repo.OrgRepository;
import com.mplus.service.OrgService;
import com.mplus.utils.tree.entity.CheckboxTreeNode;
import com.mplus.utils.tree.entity.TreeNode;

@Service
@Transactional
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgRepository orgRepository;

	@Override
	public Org saveOrg(Org org) {
		if (!StringUtils.isEmpty(org.getId())) {
			throw new RuntimeException("object id is not null or empty");
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
		org.setStatus(Status.DELETED.getCode());
		orgRepository.save(org);
	}

	@Override
	@Transactional(readOnly = true)
	public Org findOneByCode(String orgCode) {
		return orgRepository.findOneByCode(orgCode, Status.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Org> findOrgsByParent(String parentOrgId) {
		return orgRepository.findOrgsByParent(parentOrgId, Status.NORMAL.getCode());
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreeNode> getNodes(String id) {
		List<Org> orgs = this.findOrgsByParent(id);
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
