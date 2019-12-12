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
import com.mplus.system.entity.Dict;
import com.mplus.system.repo.DictRepository;
import com.mplus.system.vo.DictDataVo;
import com.mplus.system.vo.DictVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DictController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class DictController {
    private DictRepository dictRepository;

    @PostMapping(value = "/dicts")
    @SneakyThrows
    public Result<String> addDict(@RequestBody DictVo dictVo) {
        Dict dict = new Dict();
        MBeanUtils.copyPropertiesIgnoreNull(dictVo, dict);
        dict.setStatus(Status.ENABLE.code());
        dictRepository.save(dict);
        return Result.success(dict.getId());
    }

    @PutMapping(value = "/dicts/{dictId}")
    @SneakyThrows
    public Result<String> updateDict(@PathVariable String dictId, @RequestBody DictVo dictVo) {
        Dict dict = new Dict();
        dict.setId(dictId);
        dict.setDataState(DataState.NORMAL.code());
        dict = dictRepository.findOne(Example.of(dict)).get();
        if(dict.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to update");
        }
        MBeanUtils.copyPropertiesIgnoreNull(dictVo, dict);
        dictRepository.save(dict);
        return Result.success(dict.getId());
    }

    @DeleteMapping(value = "/dicts/{dictId}")
    @SneakyThrows
    public Result<String> deleteDict(@PathVariable String dictId) {
        Dict dict = new Dict();
        dict.setId(dictId);
        dict.setDataState(DataState.NORMAL.code());
        dict = dictRepository.findOne(Example.of(dict)).get();
        if(dict.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to delete");
        }
        dict.setDataState(DataState.DELETED.code());
        dictRepository.save(dict);
        return Result.success(dict.getId());
    }

    @GetMapping(value = "/dicts/page")
    public Result<PageVo<DictVo>> findPageDicts(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) Boolean buildIn,
            @RequestParam(required = false) String dataState
    ) {
        Dict dict = new Dict();
        if(!StringUtils.isEmpty(dictName)) {
            dict.setDictName(dictName);
        }
        if(!StringUtils.isEmpty(dictType)) {
            dict.setDictType(dictType);
        }
        if(null != buildIn) {
            dict.setBuildIn(buildIn);
        }
        if(!StringUtils.isEmpty(dataState)) {
            dict.setDataState(dataState);
        }

        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("createTime"));
        Page<Dict> dicts = dictRepository.findAll(Example.of(dict), pageable);

        PageVo<DictVo> pageVo = new PageVo<>();
        pageVo.setTotalElements(dicts.getTotalElements());
        pageVo.setTotalPages(dicts.getTotalPages());
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(pageSize);
        List<DictVo> dictVos = new ArrayList<>();
        dicts.getContent().stream().forEach(_dict -> {
            DictVo dictVo = new DictVo();
            MBeanUtils.copyPropertiesIgnoreNull(_dict, dictVo);
            dictVos.add(dictVo);
        });
        pageVo.setContent(dictVos);
        return Result.success(pageVo);
    }

    @GetMapping(value = "/dicts/{dictId}")
    public Result<DictVo> findDict(@PathVariable String dictId) {
        Dict dict = new Dict();
        dict.setId(dictId);
        dict.setDataState(DataState.NORMAL.code());
        dict = dictRepository.findOne(Example.of(dict)).get();
        DictVo dictVo = new DictVo();
        MBeanUtils.copyPropertiesIgnoreNull(dict, dictVo);
        return Result.success(dictVo);
    }
}
