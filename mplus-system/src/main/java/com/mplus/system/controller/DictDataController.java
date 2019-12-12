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
import com.mplus.system.entity.Dict;
import com.mplus.system.entity.DictData;
import com.mplus.system.repo.DictDataRepository;
import com.mplus.system.repo.DictRepository;
import com.mplus.system.vo.DictDataVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DictDataController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class DictDataController {
    private DictRepository dictRepository;
    private DictDataRepository dictDataRepository;

    @PostMapping(value = "/dicts/{dictId}/data")
    @SneakyThrows
    public Result<String> addDictData(@PathVariable String dictId, @RequestBody DictDataVo dictDataVo) {
        Dict dict = new Dict();
        dict.setId(dictId);
        dict.setDataState(DataState.NORMAL.code());
        dict = dictRepository.findOne(Example.of(dict)).get();
        DictData dictData = new DictData();
        MBeanUtils.copyPropertiesIgnoreNull(dictDataVo, dictData);
        dictData.setStatus(Status.ENABLE.code());
        dictData.setDict(dict);
        dictDataRepository.save(dictData);
        return Result.success(dictData.getId());
    }

    @PutMapping(value = "/dicts/{dictId}/data/{dictDataId}")
    @SneakyThrows
    public Result<String> updateDictData(@PathVariable String dictId, @PathVariable String dictDataId,
                                         @RequestBody DictDataVo dictDataVo) {
        DictData dictData = findOne(dictId, dictDataId);
        if(dictData.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to update");
        }
        MBeanUtils.copyPropertiesIgnoreNull(dictDataVo, dictData);
        dictDataRepository.save(dictData);
        return Result.success(dictData.getId());
    }

    @DeleteMapping(value = "/dicts/{dictId}/data/{dictDataId}")
    @SneakyThrows
    public Result<String> deleteDictData(@PathVariable String dictId, @PathVariable String dictDataId) {
        DictData dictData = findOne(dictId, dictDataId);
        if(dictData.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to delete");
        }
        dictData.setDataState(DataState.DELETED.code());
        dictDataRepository.save(dictData);
        return Result.success(dictData.getId());
    }

    private DictData findOne(String dictId, String dictDataId) {
        DictData dictData = new DictData();
        dictData.setId(dictDataId);
        dictData.setDataState(DataState.NORMAL.code());
        dictData = dictDataRepository.findOne(Example.of(dictData)).get();
        if(!dictId.equals(dictData.getDict().getId())) {
            throw new RuntimeException(String.format("this object is not belong to [%s]", dictId));
        }
        return dictData;
    }

    @GetMapping(value = "/dicts/{dictId}/data")
    @SneakyThrows
    public Result<List<DictDataVo>> findDictDatas(@PathVariable String dictId) {
        Dict dict = new Dict();
        dict.setId(dictId);
        DictData dictData = new DictData();
        dictData.setDataState(DataState.NORMAL.code());
        dictData.setDict(dict);
        List<DictData> list = dictDataRepository.findAll(Example.of(dictData), Sort.by("createTime"));
        List<DictDataVo> retList = new ArrayList<>();
        list.stream().forEach(data -> {
            DictDataVo vo = new DictDataVo();
            MBeanUtils.copyPropertiesIgnoreNull(data, vo);
            retList.add(vo);
        });
        return Result.success(retList);
    }

    @GetMapping(value = "/dicts/{dictId}/data/{dictDataId}")
    @SneakyThrows
    public Result<DictDataVo> findDictData(@PathVariable String dictId, @PathVariable String dictDataId) {
        DictData dictData = findOne(dictId, dictDataId);
        DictDataVo vo = new DictDataVo();
        MBeanUtils.copyPropertiesIgnoreNull(dictData, vo);
        return Result.success(vo);
    }
}
