package com.mplus.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.advice.Result;
import com.mplus.entity.Org;
import com.mplus.service.OrgService;

@RestController
@RequestMapping(value = "/v1/org")
public class OrgController {

	@Autowired
	private OrgService orgService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Org> add(@RequestBody Org org) {
		orgService.saveOrg(org);
		return Result.sucess(org);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result<List<Org>> getAll() {
//		Org parent = orgService.findOneByCode("0");
//		List<Org> orgs = orgService.findOrgsByParent(parent.getOrgCode());
		List<Org> orgs = orgService.find(Collections.emptyMap());
		return Result.sucess(orgs);
	}
	
	@RequestMapping(value = "/{orgCode}", method = RequestMethod.GET)
	public Result<Org> getOneByCode(@PathVariable String orgCode) {
		Org org = orgService.findOneByCode(orgCode);
		return Result.sucess(org);
	}
	
	@RequestMapping(value = "/{orgCode}/children", method = RequestMethod.GET)
	public Result<List<Org>> getChildren(@PathVariable String orgCode) {
		Org parent = orgService.findOneByCode(orgCode);
		List<Org> children = orgService.findOrgsByParent(parent.getOrgCode());
		return Result.sucess(children);
	}
}