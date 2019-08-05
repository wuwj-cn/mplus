package com.mplus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.advice.Result;
import com.mplus.entity.Module;
import com.mplus.enums.Status;
import com.mplus.service.ModuleService;

@RestController
@RequestMapping(value = "/api/module")
public class ModuleController {

	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result add(@RequestBody Module module) {
		moduleService.save(module);
		return Result.sucess(module);
	}
	
	@RequestMapping(value = "/list/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Result list(
			@PathVariable int pageIndex, 
			@PathVariable int pageSize, 
			@RequestParam(required=false) String moduleName, 
			@RequestParam(required=false) String moduleCode, 
			@RequestParam(required=false) String status,
			@RequestParam(required=false) String sortProperties,
			@RequestParam(required=false) String sortDirection) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(moduleName)) searchParams.put("moduleName:like", moduleName);
		if(StringUtils.isNotBlank(moduleCode)) searchParams.put("moduleCode:like", moduleCode);
		if(StringUtils.isBlank(status)) {
			searchParams.put("status:ne", Status.DELETED.getCode());
		} else {
			searchParams.put("status:eq", status);
		}
		
		List<String> properties = new ArrayList<String>();
		if(StringUtils.isNotBlank(sortProperties)) {
			properties.add(sortProperties);
		} else {
			properties.add("createDate"); //默认排序条件
		}
		
		Direction direction = Direction.DESC; //默认倒序
		if(StringUtils.isNotBlank(sortDirection) && sortDirection.equals("ascend")) {
			direction = Direction.ASC;
		}
		
		Pageable pageable = new PageRequest(pageIndex, pageSize, new Sort(direction, properties));
		Page<Module> modules = moduleService.list(searchParams, pageable);
		return Result.sucess(modules);
	}
	
	@RequestMapping(value = "/get/{moduleCode}", method = RequestMethod.GET)
	public Result getOneByCode(@PathVariable String moduleCode) {
		return Result.sucess(null);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Result update(@RequestBody Module module) {
		moduleService.update(module);
		return Result.sucess(module);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public Result delete(@PathVariable String id) {
		moduleService.delete(id);
		return Result.sucess(id);
	}
}
