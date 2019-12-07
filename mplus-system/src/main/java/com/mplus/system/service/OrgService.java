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
