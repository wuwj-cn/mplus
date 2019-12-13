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
import com.mplus.common.enums.Status;
import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.common.vo.PageVo;
import com.mplus.system.entity.DictType;
import com.mplus.system.repo.DictTypeRepository;
import com.mplus.system.vo.DictTypeVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DictTypeController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class DictTypeController {
    private DictTypeRepository dictTypeRepository;

    @PostMapping(value = "/dict/types")
    @SneakyThrows
    public Result<String> addDictType(@RequestBody DictTypeVo dictTypeVo) {
        DictType type = new DictType();
        MBeanUtils.copyPropertiesIgnoreNull(dictTypeVo, type);
        type.setStatus(Status.ENABLE.code());
        dictTypeRepository.save(type);
        return Result.success(type.getId());
    }

    @PutMapping(value = "/dict/types/{typeCode}")
    @SneakyThrows
    public Result<String> updateDictType(@PathVariable String typeCode, @RequestBody DictTypeVo dictTypeVo) {
        DictType type = new DictType();
        type.setTypeCode(typeCode);
        type.setDataState(DataState.NORMAL.code());
        type = dictTypeRepository.findOne(Example.of(type)).get();
        if(type.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to update");
        }
        MBeanUtils.copyPropertiesIgnoreNull(dictTypeVo, type);
        dictTypeRepository.save(type);
        return Result.success(type.getId());
    }

    @DeleteMapping(value = "/dict/types/{typeCode}")
    @SneakyThrows
    public Result<String> deleteDictType(@PathVariable String typeCode) {
        DictType type = new DictType();
        type.setTypeCode(typeCode);
        type.setDataState(DataState.NORMAL.code());
        type = dictTypeRepository.findOne(Example.of(type)).get();
        if(type.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to delete");
        }
        type.setDataState(DataState.DELETED.code());
        dictTypeRepository.save(type);
        return Result.success(type.getId());
    }

    @GetMapping(value = "/dict/types/page")
    public Result<PageVo<DictTypeVo>> findPageDictTypes(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) Boolean buildIn,
            @RequestParam(required = false) String status
    ) {
        DictType type = new DictType();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if(!StringUtils.isEmpty(typeCode)) {
            type.setTypeCode(typeCode);
            matcher = matcher.withMatcher("typeCode", match -> match.contains());
        }
        if(!StringUtils.isEmpty(typeName)) {
            type.setTypeName(typeName);
            matcher = matcher.withMatcher("typeName", match -> match.contains());
        }
        if(null != buildIn) {
            type.setBuildIn(buildIn);
        }
        if(!StringUtils.isEmpty(status)) {
            type.setStatus(status);
        }

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("createTime"));
        Page<DictType> dicts = dictTypeRepository.findAll(Example.of(type, matcher), pageable);

        PageVo<DictTypeVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(dicts.getTotalElements());
        pageVo.setTotalPages(dicts.getTotalPages());
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(pageSize);
        List<DictTypeVo> dictTypeVos = new ArrayList<>();
        dicts.getContent().stream().forEach(_dict -> {
            DictTypeVo dictTypeVo = new DictTypeVo();
            MBeanUtils.copyPropertiesIgnoreNull(_dict, dictTypeVo);
            dictTypeVos.add(dictTypeVo);
        });
        pageVo.setContent(dictTypeVos);
        return Result.success(pageVo);
    }

    @GetMapping(value = "/dict/types/{typeCode}")
    public Result<DictTypeVo> findDictType(@PathVariable String typeCode) {
        DictType type = new DictType();
        type.setTypeCode(typeCode);
        type.setDataState(DataState.NORMAL.code());
        type = dictTypeRepository.findOne(Example.of(type)).get();
        DictTypeVo dictTypeVo = new DictTypeVo();
        MBeanUtils.copyPropertiesIgnoreNull(type, dictTypeVo);
        return Result.success(dictTypeVo);
    }
}
