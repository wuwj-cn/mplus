package com.mplus.system.service;

import java.util.List;

import com.mplus.system.entity.Org;
import com.mplus.common.service.BaseService;
import com.mplus.common.utils.tree.service.TreeService;

public interface OrgService extends TreeService, BaseService<Org, String> {

	Org saveOrg(Org org);
	
	Org updateOrg(Org org);
	
	void removeOrg(Org org);
	
	Org findOneByCode(String orgCode);
	
	List<Org> findOrgsByParent(String parentOrgCode);
}
