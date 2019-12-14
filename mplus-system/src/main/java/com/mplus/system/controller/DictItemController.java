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
import com.mplus.system.entity.DictType;
import com.mplus.system.entity.DictItem;
import com.mplus.system.repo.DictItemRepository;
import com.mplus.system.repo.DictTypeRepository;
import com.mplus.system.vo.DictItemVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DictItemController
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class DictItemController {
    private DictTypeRepository dictTypeRepository;
    private DictItemRepository dictItemRepository;

    @PostMapping(value = "/dict/types/{typeCode}/items")
    @SneakyThrows
    public Result<String> addDictItem(@PathVariable String typeCode, @RequestBody DictItemVo dictItemVo) {
        DictType type = new DictType();
        type.setTypeCode(typeCode);
        type.setDataState(DataState.NORMAL.code());
        type = dictTypeRepository.findOne(Example.of(type)).get();
        DictItem item = new DictItem();
        MBeanUtils.copyPropertiesIgnoreNull(dictItemVo, item);
        item.setStatus(Status.ENABLE.code());
        item.setDictType(type);
        dictItemRepository.save(item);
        return Result.success(item.getId());
    }

    @PutMapping(value = "/dict/types/{typeCode}/items/{itemCode}")
    @SneakyThrows
    public Result<String> updateDictItem(@PathVariable String typeCode, @PathVariable String itemCode,
                                         @RequestBody DictItemVo dictItemVo) {
        DictItem item = findOne(typeCode, itemCode);
        if(item.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to update");
        }
        MBeanUtils.copyPropertiesIgnoreNull(dictItemVo, item);
        dictItemRepository.save(item);
        return Result.success(item.getId());
    }

    @DeleteMapping(value = "/dict/types/{typeCode}/items/{itemCode}")
    @SneakyThrows
    public Result<String> deleteDictItem(@PathVariable String typeCode, @PathVariable String itemCode) {
        DictItem item = findOne(typeCode, itemCode);
        if(item.getBuildIn()) {
            throw new RuntimeException("this object is build-in, it's not allow to delete");
        }
        item.setDataState(DataState.DELETED.code());
        dictItemRepository.save(item);
        return Result.success(item.getId());
    }

    private DictItem findOne(String typeCode, String itemCode) {
        DictItem item = new DictItem();
        item.setItemCode(itemCode);
        item.setDataState(DataState.NORMAL.code());
        item = dictItemRepository.findOne(Example.of(item)).get();
        if(!typeCode.equals(item.getDictType().getTypeCode())) {
            throw new RuntimeException(String.format("this object is not belong to [%s]", typeCode));
        }
        return item;
    }

    @GetMapping(value = "/dict/types/{typeCode}/items")
    @SneakyThrows
    public Result<List<DictItemVo>> findDictItems(@PathVariable String typeCode) {
        DictType type = new DictType();
        type.setTypeCode(typeCode);
        DictItem item = new DictItem();
        item.setDataState(DataState.NORMAL.code());
        item.setDictType(type);
        List<DictItem> list = dictItemRepository.findAll(Example.of(item), Sort.by("createTime"));
        List<DictItemVo> retList = new ArrayList<>();
        list.stream().forEach(data -> {
            DictItemVo vo = new DictItemVo();
            MBeanUtils.copyPropertiesIgnoreNull(data, vo);
            retList.add(vo);
        });
        return Result.success(retList);
    }

    @GetMapping(value = "/dict/types/{typeCode}/items/{itemCode}")
    @SneakyThrows
    public Result<DictItemVo> findDictItem(@PathVariable String typeCode, @PathVariable String itemCode) {
        DictItem item = findOne(typeCode, itemCode);
        DictItemVo vo = new DictItemVo();
        MBeanUtils.copyPropertiesIgnoreNull(item, vo);
        return Result.success(vo);
    }
}
