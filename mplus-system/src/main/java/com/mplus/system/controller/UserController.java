package com.mplus.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mplus.common.response.Result;
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

import com.mplus.system.entity.User;
import com.mplus.system.service.UserService;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Result<User> add(@RequestBody User user) {
		user.setPassword("123456"); //初始默认密码
		userService.saveUser(user);
		return Result.sucess(user);	
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Result<User> update(@RequestBody User user) {
		userService.update(user);
		return Result.sucess(user);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public Result<User> remove(@PathVariable String userCode) {
		User user = userService.findOneByCode(userCode);
		userService.delete(user);
		return Result.sucess(user);
	}
	
//	@RequestMapping(value = "/{userCode}", method = RequestMethod.GET)
//	public Result<User> list(@PathVariable String userCode) {
//		User user = userService.findOneByCode(userCode);
//		return Result.sucess(user);
//	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public Result<User> getUserByUsername(@PathVariable String username) {
		User user = userService.findByUsername(username);
		return Result.sucess(user);
	}
	
	@RequestMapping(value = "/org/{orgId}", method = RequestMethod.GET)
	public Result<List<User>> listByOrg(@PathVariable String orgId) {
		List<User> users = userService.findByOrgId(orgId);
		return Result.sucess(users);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Result<Page<User>> list(
			@RequestParam int pi, 
			@RequestParam int ps, 
			@RequestParam(required=false) String username, 
			@RequestParam(required=false) String nickName, 
			@RequestParam(required=false) String status,
			@RequestParam(required=false) String sortProperties,
			@RequestParam(required=false) String sortDirection) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(username)) searchParams.put("username:like", username);
		if(StringUtils.isNotBlank(nickName)) searchParams.put("nickName:like", nickName);
		
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
		
		Pageable pageable = PageRequest.of(pi, ps, new Sort(direction, properties));
		Page<User> users = userService.findPage(searchParams, pageable);
		return Result.sucess(users);
	}
	
}