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
package com.mplus.system.controller;

import com.mplus.common.enums.DataState;
import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.common.utils.jpa.JpaUtils;
import com.mplus.common.utils.jpa.QueryParam;
import com.mplus.common.utils.jpa.QueryType;
import com.mplus.common.utils.tree.entity.TreeNode;
import com.mplus.system.entity.Org;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.vo.OrgVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class OrgController {

	private OrgRepository orgRepository;

	@PostMapping(value = "/orgs")
	@SneakyThrows
	public Result<String> add(@RequestBody OrgVo orgVo) {
		Org org = new Org();
		MBeanUtils.copyPropertiesIgnoreNull(orgVo, org);

		if (!StringUtils.isEmpty(org.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		String parentOrgId = orgVo.getParentOrgId();
		if(StringUtils.isEmpty(parentOrgId)) {
			parentOrgId = Org.ROOT_ORG_ID;
		}
		Org parentOrg = orgRepository.findOneByOrgId(parentOrgId, DataState.NORMAL.code());
		org.setParentOrg(parentOrg);
		org.setOrgId(generateMaxOrgId(org.getParentOrg().getOrgId()));
		orgRepository.save(org);

		return Result.success(org.getOrgId());
	}

	/**
	 * 生成下级机构最大orgId
	 *
	 * @param parentOrgId 上级机构ID
	 * @return 当前可用的下级机构最大ID
	 */
	@SneakyThrows
	private String generateMaxOrgId(String parentOrgId) {
		List<Org> orgs = orgRepository.findOrgsByParent(parentOrgId, DataState.NORMAL.code());
		if(orgs.isEmpty()) {
			if(parentOrgId.equals(Org.ROOT_ORG_ID)) {
				return "01";
			} else {
				return parentOrgId.concat("01");
			}
		}
		orgs.sort((a, b) -> b.getOrgId().compareTo(a.getOrgId()));
		String maxLevelNo = orgs.get(0).getOrgId().substring(parentOrgId.length());
		Integer maxOrgId = Integer.parseInt(maxLevelNo);
		if(maxOrgId >= 99) {
			throw new RuntimeException("orgId range of values is 0-99");
		}
		String temp = "";
		if(maxOrgId <= 9) {
			temp = "0".concat(String.valueOf(maxOrgId + 1));
		} else {
			temp = temp.concat(String.valueOf(maxOrgId + 1));
		}
		return parentOrgId.equals(Org.ROOT_ORG_ID) ? temp : parentOrgId.concat(temp);
	}

	@PutMapping(value = "/orgs/{orgId}")
	@SneakyThrows
	public Result<Object> update(@PathVariable String orgId, @RequestBody OrgVo orgVo) {
		Org org = orgRepository.findOneByOrgId(orgId, DataState.NORMAL.code());
		MBeanUtils.copyPropertiesIgnoreNull(orgVo, org);
		orgRepository.save(org);
		return Result.success(org.getOrgId());
	}

	@DeleteMapping(value = "/orgs/{orgId}")
	@SneakyThrows
	public Result<Object> delete(@PathVariable String orgId) {
		List<QueryParam> searchParams = new ArrayList<>();
		QueryParam param = null;
		param = QueryParam.build("orgId", QueryType.eq, orgId);
		searchParams.add(param);
		Specification<Org> spec = JpaUtils.buildSpecification(searchParams);
		Org org = orgRepository.findOne(spec).get();
		org.setDataState(DataState.DELETED.code());
		orgRepository.save(org);
		return Result.success(org.getOrgId());
	}

	@GetMapping(value = "/orgs/{orgId}/children")
	@SneakyThrows
	public Result<List<OrgVo>> findOrgsByParent(@PathVariable String orgId) {
		List<OrgVo> orgVos = this.getOrgVosByParent(orgId);
		return Result.success(orgVos);
	}

	@GetMapping(value = "/orgs/{orgId}")
	@SneakyThrows
	public Result<OrgVo> findOrg(@PathVariable String orgId) {
		Org org = orgRepository.findOneByOrgId(orgId, DataState.NORMAL.code());

		OrgVo orgVo = new OrgVo();
		MBeanUtils.copyPropertiesIgnoreNull(org, orgVo);
		orgVo.setParentOrgId(org.getParentOrg().getOrgId());
		orgVo.setParentOrgName(org.getParentOrg().getOrgName());
		return Result.success(orgVo);
	}

	@GetMapping(value = "/orgs/{orgId}/tree")
	public Result<OrgVo> findAllOrgs(@PathVariable String orgId) {
		Org _org = new Org();
		_org.setOrgId(orgId);
		Org root = orgRepository.findOne(Example.of(_org)).get();
		OrgVo rootVo = new OrgVo();
		MBeanUtils.copyPropertiesIgnoreNull(root, rootVo);
		rootVo.setParentOrgId(root.getParentOrg().getOrgId());
		rootVo.setParentOrgName(root.getParentOrg().getOrgName());
		buildOrgTree(rootVo);
		return Result.success(rootVo);
	}

	private void buildOrgTree(OrgVo root) {
		List<OrgVo> orgVos = this.getOrgVosByParent(root.getOrgId());
		orgVos.stream().forEach(orgVo -> {
			buildOrgTree(orgVo);
		});
		root.setChildren(orgVos);
	}

	/**
	 * 根据上级机构ID查找构建下属的orgVo
	 *
	 * @param parentOrgId 上级机构ID
	 * @return 下属的orgVo列表
	 */
	private List<OrgVo> getOrgVosByParent(String parentOrgId) {
		Org parentOrg = new Org();
		parentOrg.setOrgId(parentOrgId);
		Org _org = new Org();
		_org.setParentOrg(parentOrg);
		_org.setDataState(DataState.NORMAL.code());
		List<Org> children = orgRepository.findAll(Example.of(_org), Sort.by("orgId"));
		List<OrgVo> orgVos = new ArrayList<>();
		children.stream().forEach(org -> {
			OrgVo orgVo = new OrgVo();
			MBeanUtils.copyPropertiesIgnoreNull(org, orgVo);
			orgVo.setParentOrgId(org.getParentOrg().getOrgId());
			orgVo.setParentOrgName(org.getParentOrg().getOrgName());
			orgVos.add(orgVo);
		});
		return orgVos;
	}
}