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
		return Result.sucess(menu);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Result<List<Menu>> getAll() {
		Menu parent = menuService.findOneByCode("0");
		List<Menu> menus = menuService.findMenusByParent(parent.getId());
		return Result.sucess(menus);
	}
	
	@RequestMapping(value = "/{menuCode}", method = RequestMethod.GET)
	public Result<Menu> getOneByCode(@PathVariable String menuCode) {
		Menu menu = menuService.findOneByCode(menuCode);
		return Result.sucess(menu);
	}
	
	@RequestMapping(value = "/{menuCode}/children", method = RequestMethod.GET)
	public Result<List<Menu>> getChildren(@PathVariable String menuCode) {
		Menu parent = menuService.findOneByCode(menuCode);
		List<Menu> children = menuService.findMenusByParent(parent.getId());
		return Result.sucess(children);
	}
}
