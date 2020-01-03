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
import com.mplus.system.entity.Menu;
import com.mplus.system.entity.Module;
import com.mplus.system.repo.MenuRepository;
import com.mplus.system.repo.ModuleRepository;
import com.mplus.system.vo.MenuVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class MenuController {
	private MenuRepository menuRepository;
	private ModuleRepository moduleRepository;

	private static final String ROOT_MENU_CODE = "0";
	
	@PostMapping(value = "/menus")
	@SneakyThrows
	public Result<String> addMenu( @RequestBody MenuVo menuVo) {
		Assert.notNull(menuVo.getModuleCode(), "module can not be empty");
		Menu menu = new Menu();
		MBeanUtils.copyPropertiesIgnoreNull(menuVo, menu);
		Assert.isNull(menu.getId(), "object id must be null");
		String moduleCode = menuVo.getModuleCode();
		Module module = moduleRepository.findModuleByModuleCodeAndDataState(moduleCode, DataState.NORMAL.code());
		menu.setModule(module);

		String parentMenuCode = menuVo.getParentMenuCode();
		if(StringUtils.isEmpty(parentMenuCode)) {
			parentMenuCode = ROOT_MENU_CODE;
		}
		Menu parent = menuRepository.findMenuByMenuCodeAndDataState(parentMenuCode, DataState.NORMAL.code());
		menu.setParentMenu(parent);
		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	@PutMapping(value = "/menus/{menuCode}")
	@SneakyThrows
	public Result<String> updateMenu(@PathVariable String menuCode,
									 @RequestBody MenuVo menuVo) {
		Assert.isNull(menuVo.getMenuCode(), "not allowed parameter");
		Menu menu = menuRepository.findMenuByMenuCodeAndDataState(menuCode, DataState.NORMAL.code());
		MBeanUtils.copyPropertiesIgnoreNull(menuVo, menu);

		String parentMenuCode = menuVo.getParentMenuCode();
        if(StringUtils.isEmpty(parentMenuCode)) {
        	parentMenuCode = ROOT_MENU_CODE;
        }
		Menu parent = menuRepository.findMenuByMenuCodeAndDataState(parentMenuCode, DataState.NORMAL.code());
		menu.setParentMenu(parent);

		String moduleCode = menuVo.getModuleCode();
		Assert.notNull(moduleCode, "module can not be empty");
		if(!moduleCode.equals(menu.getModule().getModuleCode())) {
			Module module = moduleRepository.findModuleByModuleCodeAndDataState(moduleCode, DataState.NORMAL.code());
			menu.setModule(module);
		}

		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	@DeleteMapping(value = "/modules/{moduleCode}/menus/{menuCode}")
	@SneakyThrows
	public Result<String> deleteMenu(@PathVariable String moduleCode, @PathVariable String menuCode) {
		Menu menu = menuRepository.findMenuByMenuCodeAndDataStateAndModule_moduleCode(menuCode,
				DataState.NORMAL.code(), moduleCode);
		Assert.notNull(menu, "menu is null");
		menu.setDataState(DataState.DELETED.code());
		menuRepository.save(menu);
		return Result.success(menu.getId());
	}

	@GetMapping(value = "/menus")
    public Result<List<MenuVo>> findMenusByModule(@RequestParam(required = false) String moduleCode,
                                                  @RequestParam(required = false) String menuCode,
                                                  @RequestParam(required = false) String menuName,
                                                  @RequestParam(required = false) String parentMenuCode) {

		Specification<Menu> spec = (Specification<Menu>) (root, query, cb) -> {
			Predicate predicate = cb.conjunction();//动态SQL表达式
			List<Expression<Boolean>> exList = predicate.getExpressions();//动态SQL表达式集合
			exList.add(cb.equal(root.get("dataState"), DataState.NORMAL.code()));

			if (!StringUtils.isEmpty(moduleCode)) {
				Join<Menu, Module> moduleJoin = root.join("module", JoinType.INNER);
				exList.add(cb.equal(moduleJoin.get("moduleCode"), moduleCode));
				exList.add(cb.equal(moduleJoin.get("dataState"), DataState.NORMAL.code()));
			}
			if(!StringUtils.isEmpty(menuCode)) {
				exList.add(cb.like(root.get("menuCode"), "%" + menuCode + "%"));
			}
			if(!StringUtils.isEmpty(menuName)) {
				exList.add(cb.like(root.get("menuName"), "%" + menuName + "%"));
			}
			Join<Menu, Menu> menuJoin = root.join("parentMenu", JoinType.INNER);
			String _parentMenuCode = parentMenuCode;
			if(StringUtils.isEmpty(_parentMenuCode)) {
				_parentMenuCode = ROOT_MENU_CODE;
			}
			exList.add(cb.equal(menuJoin.get("menuCode"), _parentMenuCode));
			exList.add(cb.equal(menuJoin.get("dataState"), DataState.NORMAL.code()));
			return predicate;
		};
		List<Menu> menus = menuRepository.findAll(spec, Sort.by("createTime"));
        List<MenuVo> retList = new ArrayList<>();
        menus.stream().forEach(_menu -> {
            MenuVo vo = buildMenuVo(_menu);
            retList.add(vo);
        });
        return Result.success(retList);
    }

    @GetMapping(value = "/menus/{menuCode}")
	@SneakyThrows
    public Result<MenuVo> findMenu( @PathVariable String menuCode) {
	    Menu menu = menuRepository.findMenuByMenuCodeAndDataState(menuCode, DataState.NORMAL.code());
        MenuVo menuVo = buildMenuVo(menu);
        return Result.success(menuVo);
    }

    private MenuVo buildMenuVo(Menu menu) {
        MenuVo menuVo = new MenuVo();
        MBeanUtils.copyPropertiesIgnoreNull(menu, menuVo);
        if (null != menu.getModule()) {
			menuVo.setModuleCode(menu.getModule().getModuleCode());
			menuVo.setModuleName(menu.getModule().getModuleName());
		}
        if(null != menu.getParentMenu()) {
			menuVo.setParentMenuCode(menu.getParentMenu().getMenuCode());
			menuVo.setParentMenuName(menu.getParentMenu().getMenuName());
		}
        return menuVo;
    }
}
