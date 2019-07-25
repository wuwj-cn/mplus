package com.mplus.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mplus.model.User;
import com.mplus.utils.Result;

@FeignClient(name = "mplus-core")
@Service
public interface UserService {
	@RequestMapping(value = "/api/user/{username}", method = RequestMethod.GET)
	Result<User> getUserInfo(@PathVariable("username") String username);
}
