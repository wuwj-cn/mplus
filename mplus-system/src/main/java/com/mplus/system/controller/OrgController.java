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
import com.mplus.common.utils.jpa.QueryTypeEnum;
import com.mplus.system.entity.Org;
import com.mplus.system.entity.User;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.vo.OrgVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class OrgController {

	private OrgRepository orgRepository;

	@PostMapping(value = "/orgs")
	public Result<String> add(@RequestBody OrgVo orgVo) {
		Org org = new Org();
		MBeanUtils.copyPropertiesIgnoreNull(orgVo, org);

		if (!StringUtils.isEmpty(org.getId())) {
			throw new RuntimeException("object id is not null or empty");
		}
		if(StringUtils.isEmpty(org.getParentOrgId())) {
			org.setParentOrgId(Org.ROOT_ORG_ID);
		}
		org.setOrgId(generateMaxOrgId(org.getParentOrgId()));
		orgRepository.save(org);

		return Result.success(org.getOrgId());
	}

	@PutMapping(value = "/orgs/{orgId}")
	public Result<Object> update(@PathVariable String orgId, @RequestBody OrgVo orgVo) {
		Org org = orgRepository.findOneByOrgId(orgId, DataState.NORMAL.code());
		MBeanUtils.copyPropertiesIgnoreNull(orgVo, org);
		orgRepository.save(org);
		return Result.success(org.getOrgId());
	}

	@DeleteMapping(value = "/orgs/{orgId}")
	public Result<Object> delete(@PathVariable String orgId) {
		Org org = new Org();
		List<QueryParam> searchParams = new ArrayList<>();
		QueryParam param = null;
		param = new QueryParam("orgId", QueryTypeEnum.eq, orgId);
		searchParams.add(param);
		param = new QueryParam("dataState", QueryTypeEnum.eq, DataState.NORMAL.code());
		searchParams.add(param);
		Specification<Org> spec = JpaUtils.buildSpecification(searchParams);
//		Optional<Org> org = orgRepository.findOne(spec);
//		org.setDataState(DataState.DELETED.code());
//		orgRepository.save(org);
		return Result.success(org.getOrgId());
	}

	/**
	 * 生成下级机构最大orgId
	 *
	 * @param parentOrgId 上级机构ID
	 * @return 当前可用的下级机构最大ID
	 */
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
		return parentOrgId.concat(temp);
	}

}