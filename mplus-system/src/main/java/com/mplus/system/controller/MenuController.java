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

import java.util.List;

import com.mplus.common.response.Result;
import com.mplus.system.repo.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.system.entity.Menu;

@RestController
@RequestMapping(value = "/v1/menu")
@AllArgsConstructor
public class MenuController {

	private MenuRepository menuRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<Menu> add(@RequestBody Menu menu) {
		menuRepository.save(menu);
		return Result.success(menu);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result<List<Menu>> getAll() {
//		Menu parent = menuRepository.findOneByCode("0",);
//		List<Menu> menus = menuRepository.findMenusByParent(parent.getId());
		return Result.success(null);
	}
	
	@RequestMapping(value = "/{menuCode}", method = RequestMethod.GET)
	public Result<Menu> getOneByCode(@PathVariable String menuCode) {
//		Menu menu = menuRepository.findOneByCode(menuCode);
		return Result.success(null);
	}
	
	@RequestMapping(value = "/{menuCode}/children", method = RequestMethod.GET)
	public Result<List<Menu>> getChildren(@PathVariable String menuCode) {
//		Menu parent = menuRepository.findOneByCode(menuCode);
//		List<Menu> children = menuRepository.findMenusByParent(parent.getId());
		return Result.success(null);
	}
}
