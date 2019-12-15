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
		String parentOrgCode = orgVo.getParentOrgCode();
		if(StringUtils.isEmpty(parentOrgCode)) {
			parentOrgCode = Org.ROOT_ORG_CODE;
		}
		Org parentOrg = orgRepository.findOneByOrgCode(parentOrgCode, DataState.NORMAL.code());
		org.setParentOrg(parentOrg);
		org.setOrgCode(generateMaxOrgId(org.getParentOrg().getOrgCode()));
		orgRepository.save(org);

		return Result.success(org.getOrgCode());
	}

	/**
	 * 生成下级机构最大orgId
	 *
	 * @param parentOrgCode 上级机构code
	 * @return 当前可用的下级机构最大code
	 */
	@SneakyThrows
	private String generateMaxOrgId(String parentOrgCode) {
		List<Org> orgs = orgRepository.findOrgsByParent(parentOrgCode, DataState.NORMAL.code());
		if(orgs.isEmpty()) {
			if(parentOrgCode.equals(Org.ROOT_ORG_CODE)) {
				return "01";
			} else {
				return parentOrgCode.concat("01");
			}
		}
		orgs.sort((a, b) -> b.getOrgCode().compareTo(a.getOrgCode()));
		String maxLevelNo = orgs.get(0).getOrgCode().substring(parentOrgCode.length());
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
		return parentOrgCode.equals(Org.ROOT_ORG_CODE) ? temp : parentOrgCode.concat(temp);
	}

	@PutMapping(value = "/orgs/{orgCode}")
	@SneakyThrows
	public Result<Object> update(@PathVariable String orgCode, @RequestBody OrgVo orgVo) {
		Org org = orgRepository.findOneByOrgCode(orgCode, DataState.NORMAL.code());
		MBeanUtils.copyPropertiesIgnoreNull(orgVo, org);
		orgRepository.save(org);
		return Result.success(org.getOrgCode());
	}

	@DeleteMapping(value = "/orgs/{orgCode}")
	@SneakyThrows
	public Result<Object> delete(@PathVariable String orgCode) {
		List<QueryParam> searchParams = new ArrayList<>();
		QueryParam param = null;
		param = QueryParam.build("orgCode", QueryType.eq, orgCode);
		searchParams.add(param);
		Specification<Org> spec = JpaUtils.buildSpecification(searchParams);
		Org org = orgRepository.findOne(spec).get();
		org.setDataState(DataState.DELETED.code());
		orgRepository.save(org);
		return Result.success(org.getOrgCode());
	}

	@GetMapping(value = "/orgs/{orgCode}/children")
	@SneakyThrows
	public Result<List<OrgVo>> findOrgsByParent(@PathVariable String orgCode) {
		List<OrgVo> orgVos = this.getOrgVosByParent(orgCode);
		return Result.success(orgVos);
	}

	@GetMapping(value = "/orgs/{orgCode}")
	@SneakyThrows
	public Result<OrgVo> findOrg(@PathVariable String orgCode) {
		Org org = orgRepository.findOneByOrgCode(orgCode, DataState.NORMAL.code());

		OrgVo orgVo = new OrgVo();
		MBeanUtils.copyPropertiesIgnoreNull(org, orgVo);
		orgVo.setParentOrgCode(org.getParentOrg().getOrgCode());
		orgVo.setParentOrgName(org.getParentOrg().getOrgName());
		return Result.success(orgVo);
	}

	@GetMapping(value = "/orgs/{orgCode}/tree")
	public Result<OrgVo> findAllOrgs(@PathVariable String orgCode) {
		Org _org = new Org();
		_org.setOrgCode(orgCode);
		Org root = orgRepository.findOne(Example.of(_org)).get();
		OrgVo rootVo = new OrgVo();
		MBeanUtils.copyPropertiesIgnoreNull(root, rootVo);
		rootVo.setParentOrgCode(root.getParentOrg().getOrgCode());
		rootVo.setParentOrgName(root.getParentOrg().getOrgName());
		buildOrgTree(rootVo);
		return Result.success(rootVo);
	}

	private void buildOrgTree(OrgVo root) {
		List<OrgVo> orgVos = this.getOrgVosByParent(root.getOrgCode());
		orgVos.stream().forEach(orgVo -> {
			buildOrgTree(orgVo);
		});
		root.setChildren(orgVos);
	}

	/**
	 * 根据上级机构ID查找构建下属的orgVo
	 *
	 * @param parentOrgCode 上级机构code
	 * @return 下属的orgVo列表
	 */
	private List<OrgVo> getOrgVosByParent(String parentOrgCode) {
		Org parentOrg = new Org();
		parentOrg.setOrgCode(parentOrgCode);
		Org _org = new Org();
		_org.setParentOrg(parentOrg);
		_org.setDataState(DataState.NORMAL.code());
		List<Org> children = orgRepository.findAll(Example.of(_org), Sort.by("orgCode"));
		List<OrgVo> orgVos = new ArrayList<>();
		children.stream().forEach(org -> {
			OrgVo orgVo = new OrgVo();
			MBeanUtils.copyPropertiesIgnoreNull(org, orgVo);
			orgVo.setParentOrgCode(org.getParentOrg().getOrgCode());
			orgVo.setParentOrgName(org.getParentOrg().getOrgName());
			orgVos.add(orgVo);
		});
		return orgVos;
	}
}