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
import com.mplus.common.exception.GenericException;
import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.common.vo.PageVo;
import com.mplus.system.entity.Module;
import com.mplus.system.repo.ModuleRepository;
import com.mplus.system.vo.ModuleVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class ModuleController {

	private ModuleRepository moduleRepository;
	
	@PostMapping(value = "/modules")
	@SneakyThrows
	public Result<String> addModule(@RequestBody ModuleVo moduleVo) {
		Module module = new Module();
		MBeanUtils.copyPropertiesIgnoreNull(moduleVo, module);
		moduleRepository.save(module);
		return Result.success(module.getId());
	}

	@PutMapping(value = "/modules/{moduleCode}")
	@SneakyThrows
	public Result<String> update(@PathVariable String moduleCode, @RequestBody ModuleVo moduleVo) {
		if(!StringUtils.isEmpty(moduleVo.getModuleCode()) && !moduleCode.equals(moduleVo.getModuleCode())) {
			throw new GenericException(String.format("this update object is not consistent with [ %s ]", moduleCode));
		}
		Module module = this.findOne(moduleCode);
		MBeanUtils.copyPropertiesIgnoreNull(moduleVo, module);
		moduleRepository.save(module);
		return Result.success(module.getId());
	}

	private Module findOne(String moduleCode) {
		Module module = new Module();
		module.setModuleCode(moduleCode);
		module.setDataState(DataState.NORMAL.code());
		Optional<Module> optional = moduleRepository.findOne(Example.of(module));
		if(!optional.isPresent()) {
			throw new GenericException(String.format("object [%s] is not present", moduleCode));
		}
		module = optional.get();
		return module;
	}

	@DeleteMapping(value = "/modules/{moduleCode}")
	public Result<String> delete(@PathVariable String moduleCode) {
		Module module = this.findOne(moduleCode);
		module.setDataState(DataState.DELETED.code());
		moduleRepository.save(module);
		return Result.success(module.getId());
	}
	
	@GetMapping(value = "/modules/page")
	@SneakyThrows
	public Result<PageVo<ModuleVo>> findPageModules(
			@RequestParam int pageNumber,
			@RequestParam int pageSize,
			@RequestParam(required=false) String moduleCode,
			@RequestParam(required=false) String moduleName,
			@RequestParam(required = false) String status) {
		Module module = new Module();
		ExampleMatcher matcher = ExampleMatcher.matching();
		if(!StringUtils.isEmpty(moduleCode)) {
			module.setModuleCode(moduleCode);
			matcher = matcher.withMatcher("moduleCode", match -> match.contains());
		}
		if(!StringUtils.isEmpty(moduleName)) {
			module.setModuleName(moduleName);
			matcher = matcher.withMatcher("moduleName", match -> match.contains());
		}
		if(!StringUtils.isEmpty(status)) {
			module.setStatus(status);
			matcher = matcher.withMatcher("status", match -> match.contains());
		}

		// pageable的页号是从0开始计算，因此根据传入的页号-1
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("createTime"));
		Page<Module> modules = moduleRepository.findAll(Example.of(module, matcher), pageable);

		List<ModuleVo> list = new ArrayList<>();
		modules.stream().forEach(m -> {
			ModuleVo vo = new ModuleVo();
			MBeanUtils.copyPropertiesIgnoreNull(m, vo);
			list.add(vo);
		});
		PageVo<ModuleVo> pages = new PageVo<>();
		pages.setTotalElements(modules.getTotalElements());
		pages.setTotalPages(modules.getTotalPages());
		pages.setPageNumber(pageNumber);
		pages.setPageSize(pageSize);
		pages.setContent(list);
		return Result.success(pages);
	}

	@GetMapping(value = "/modules/{moduleCode}")
	@SneakyThrows
	public Result<ModuleVo> findModule(@PathVariable String moduleCode) {
		Module module = this.findOne(moduleCode);
		ModuleVo vo = new ModuleVo();
		MBeanUtils.copyPropertiesIgnoreNull(module, vo);
		return Result.success(vo);
	}
}
