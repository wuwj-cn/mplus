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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mplus.common.enums.DataState;
import com.mplus.common.exception.GenericException;
import com.mplus.common.response.Result;
import com.mplus.common.utils.MBeanUtils;
import com.mplus.system.entity.Module;
import com.mplus.system.repo.MenuRepository;
import com.mplus.system.repo.ModuleRepository;
import com.mplus.system.vo.MenuVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.annotations.SortNatural;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.mplus.system.entity.Menu;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class MenuController {
	private MenuRepository menuRepository;
	private ModuleRepository moduleRepository;
	
	@PostMapping(value = "/modules/{moduleCode}/menus")
	@SneakyThrows
	public Result<String> addMenu(@PathVariable String moduleCode, @RequestBody MenuVo menuVo) {
		Menu menu = new Menu();
		MBeanUtils.copyPropertiesIgnoreNull(menuVo, menu);
		Assert.notNull(menu.getId(), "object id is not null");
		Module module = moduleRepository.findModuleByModuleCodeAndDataState(moduleCode, DataState.NORMAL.code());
		menu.setModule(module);
		if(!StringUtils.isEmpty(menuVo.getParentMenuCode())) {
		    Menu parent = this.findOne(moduleCode, menuVo.getParentMenuCode());
		    menu.setParentMenu(parent);
        }
		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	@PutMapping(value = "/modules/{moduleCode}/menus/{menuCode}")
	@SneakyThrows
	public Result<String> updateMenu(@PathVariable String moduleCode,
									 @PathVariable String menuCode,
									 @RequestBody MenuVo menuVo) {
		Assert.hasText(moduleCode, "module code can not empty");
		Assert.hasText(menuCode, "menu code can not empty");
		if(!menuCode.equals(menuVo.getMenuCode())) {
			throw new GenericException(String.format("update object is not consistent with [ %s ]", menuCode));
		}
		Menu menu = this.findOne(moduleCode, menuCode);
		MBeanUtils.copyPropertiesIgnoreNull(menuVo, menu);
        if(!StringUtils.isEmpty(menuVo.getParentMenuCode())) {
            Menu parent = this.findOne(moduleCode, menuVo.getParentMenuCode());
            menu.setParentMenu(parent);
        }
		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	private Menu findOne(String moduleCode, String menuCode) {
		Module module = new Module();
		module.setModuleCode(moduleCode);
		module.setDataState(DataState.NORMAL.code());
		Menu menu = new Menu();
		menu.setMenuCode(menuCode);
		menu.setDataState(DataState.NORMAL.code());
		menu.setModule(module);
		Optional<Menu> optional = menuRepository.findOne(Example.of(menu));
		if(!optional.isPresent()) {
			throw new GenericException(String.format("object [%s] is not present", menuCode));
		}
		return optional.get();
	}

	@DeleteMapping(value = "/modules/{moduleCode}/menus/{menuCode}")
	@SneakyThrows
	public Result<String> deleteMenu(@PathVariable String moduleCode, @PathVariable String menuCode) {
		Assert.hasText(menuCode, "code can not empty");
		Menu menu = this.findOne(moduleCode, menuCode);
		menu.setDataState(DataState.DELETED.code());
		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	@GetMapping(value = "/modules/{moduleCode}/menus")
    public Result<List<MenuVo>> findMenusByModule(@PathVariable String moduleCode,
                                                  @RequestParam(required = false) String menuCode,
                                                  @RequestParam(required = false) String menuName,
                                                  @RequestParam(required = false) String parentMenuCode) {
	    Module module = new Module();
	    module.setModuleCode(moduleCode);
	    module.setDataState(DataState.NORMAL.code());
	    Menu menu = new Menu();
	    menu.setModule(module);
	    menu.setDataState(DataState.NORMAL.code());

        ExampleMatcher matcher = ExampleMatcher.matching();
        if(!StringUtils.isEmpty(menuCode)) {
            menu.setMenuCode(menuCode);
            matcher = matcher.withMatcher("menuCode", match -> match.contains());
        }
        if(!StringUtils.isEmpty(menuName)) {
            menu.setMenuName(menuName);
            matcher = matcher.withMatcher("menuName", match -> match.contains());
        }
        if(!StringUtils.isEmpty(parentMenuCode)) {
            Menu parent = new Menu();
            parent.setMenuCode(parentMenuCode);
            menu.setParentMenu(parent);
        } else {
            menu.setParentMenu(null);
        }

        List<Menu> menus = menuRepository.findAll(Example.of(menu, matcher), Sort.by("createTime"));
        List<MenuVo> retList = new ArrayList<>();
        menus.stream().forEach(_menu -> {
            MenuVo vo = buildMenuVo(_menu);
            retList.add(vo);
        });
        return Result.success(retList);
    }

    @GetMapping(value = "/modules/{moduleCode}/menus/{menuCode}")
	@SneakyThrows
    public Result<MenuVo> findMenu(@PathVariable String moduleCode, @PathVariable String menuCode) {
	    Menu menu = this.findOne(moduleCode, menuCode);
        MenuVo menuVo = buildMenuVo(menu);
        return Result.success(menuVo);
    }

    private MenuVo buildMenuVo(Menu menu) {
        MenuVo menuVo = new MenuVo();
        MBeanUtils.copyPropertiesIgnoreNull(menu, menuVo);
        menuVo.setModuleCode(menu.getModule().getModuleCode());
        menuVo.setModuleName(menu.getModule().getModuleName());
        menuVo.setParentMenuCode(menu.getParentMenu().getMenuCode());
        menuVo.setParentMenuName(menu.getParentMenu().getMenuName());
        return menuVo;
    }
}
