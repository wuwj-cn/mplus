/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import com.mplus.system.entity.Dept;
import com.mplus.system.entity.Org;
import com.mplus.system.repo.DeptRepository;
import com.mplus.system.repo.OrgRepository;
import com.mplus.system.vo.DeptVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * DeptController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class DeptController {
    private DeptRepository deptRepository;
    private OrgRepository orgRepository;

    @PostMapping(value = "/orgs/{orgCode}/depts")
    @SneakyThrows
    public Result<String> addDept(@PathVariable String orgCode, @RequestBody DeptVo deptVo) {
        Dept dept = new Dept();
        Org org = new Org();
        org.setOrgCode(orgCode);
        org.setDataState(DataState.NORMAL.code());
        org = orgRepository.findOne(Example.of(org)).get();
        dept.setOrg(org);
        if(!StringUtils.isEmpty(deptVo.getParentDeptCode())) {
            Dept parentDept = new Dept();
            parentDept.setDeptCode(deptVo.getParentDeptCode());
            parentDept.setDataState(DataState.NORMAL.code());
            parentDept = deptRepository.findOne(Example.of(parentDept)).get();
            dept.setParentDept(parentDept);
        }
        deptRepository.save(dept);
        return Result.success(dept.getId());
    }

    @PutMapping(value = "/orgs/{orgCode}/depts/{deptCode}")
    @SneakyThrows
    public Result<String> updateDept(@PathVariable String orgCode,
                                     @PathVariable String deptCode,
                                     @RequestBody DeptVo deptVo) {
        Dept dept = findOneByOrg(orgCode, deptCode);
        if(!dept.getDeptCode().equals(deptVo.getDeptCode())) {
            throw new RuntimeException(String.format("this object is inconsistent with [%s]", deptCode));
        }
        MBeanUtils.copyPropertiesIgnoreNull(deptVo, dept);
        deptRepository.save(dept);
        return Result.success(dept.getId());
    }

    @DeleteMapping(value = "/orgs/{orgCode}/depts/{deptCode}")
    @SneakyThrows
    public Result<String> deleteDept(@PathVariable String orgCode, @PathVariable String deptCode) {
        Dept dept = findOneByOrg(orgCode, deptCode);
        dept.setDataState(DataState.DELETED.code());
        deptRepository.save(dept);
        return Result.success(dept.getId());
    }

    private Dept findOneByOrg(String orgCode, String deptCode) {
        Org org = orgRepository.findOneByOrgCode(orgCode);
        Dept dept = new Dept();
        dept.setDeptCode(deptCode);
        dept.setDataState(DataState.NORMAL.code());
        dept.setOrg(org);
        dept = deptRepository.findOne(Example.of(dept)).get();
        return dept;
    }

}
