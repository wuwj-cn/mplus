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
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mplus.system.controller;

import java.util.List;

import com.mplus.common.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Menu;
import com.mplus.system.service.MenuService;

@RestController
@RequestMapping(value = "/v1/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Menu> add(@RequestBody Menu menu) {
		menuService.saveMenu(menu);
		return Result.success(menu);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result<List<Menu>> getAll() {
		Menu parent = menuService.findOneByCode("0");
		List<Menu> menus = menuService.findMenusByParent(parent.getId());
		return Result.success(menus);
	}
	
	@RequestMapping(value = "/{menuCode}", method = RequestMethod.GET)
	public Result<Menu> getOneByCode(@PathVariable String menuCode) {
		Menu menu = menuService.findOneByCode(menuCode);
		return Result.success(menu);
	}
	
	@RequestMapping(value = "/{menuCode}/children", method = RequestMethod.GET)
	public Result<List<Menu>> getChildren(@PathVariable String menuCode) {
		Menu parent = menuService.findOneByCode(menuCode);
		List<Menu> children = menuService.findMenusByParent(parent.getId());
		return Result.success(children);
	}
}
