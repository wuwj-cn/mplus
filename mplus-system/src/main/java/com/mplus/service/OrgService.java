package com.mplus.service;

import java.util.List;

import com.mplus.entity.Org;
import com.mplus.utils.tree.service.TreeService;

public interface OrgService extends TreeService {

	Org saveOrg(Org org);
	
	Org updateOrg(Org org);
	
	void removeOrg(Org org);
	
	Org findOneByCode(String orgCode);
	
	List<Org> findOrgsByParent(String parentOrgId);
}
