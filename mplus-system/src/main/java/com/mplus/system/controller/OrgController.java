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

package com.mplus.system.controller;

import java.util.Collections;
import java.util.List;

import com.mplus.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Org;
import com.mplus.system.service.OrgService;

@RestController
@RequestMapping(value = "/v1/org")
public class OrgController {

	@Autowired
	private OrgService orgService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Org> add(@RequestBody Org org) {
		orgService.saveOrg(org);
		return Result.success(org);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result<List<Org>> getAll() {
//		Org parent = orgService.findOneByCode("0");
//		List<Org> orgs = orgService.findOrgsByParent(parent.getOrgCode());
		List<Org> orgs = orgService.find(Collections.emptyMap());
		return Result.success(orgs);
	}
	
	@RequestMapping(value = "/{orgCode}", method = RequestMethod.GET)
	public Result<Org> getOneByCode(@PathVariable String orgCode) {
		Org org = orgService.findOneByCode(orgCode);
		return Result.success(org);
	}
	
	@RequestMapping(value = "/{orgCode}/children", method = RequestMethod.GET)
	public Result<List<Org>> getChildren(@PathVariable String orgCode) {
		Org parent = orgService.findOneByCode(orgCode);
		List<Org> children = orgService.findOrgsByParent(parent.getOrgId());
		return Result.success(children);
	}
	
	@RequestMapping(value = "/{orgCode}", method = RequestMethod.PUT)
	public Result<Org> update(@PathVariable String orgCode, @RequestBody Org org) {
		orgService.update(org);
		return Result.success(org);
	}
	
	@RequestMapping(value = "/{orgCode}", method = RequestMethod.DELETE)
	public Result<Org> delete(@PathVariable String orgCode) {
		Org org = orgService.findOneByCode(orgCode);
		orgService.delete(org);
		return Result.success(org);
	}
}