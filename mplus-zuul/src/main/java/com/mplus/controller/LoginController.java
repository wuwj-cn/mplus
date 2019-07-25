package com.mplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@ResponseBody
	@GetMapping("/")
	public String index() {
//		return "forward:/auth/api/v1/login";
		return "welcome to mplus...";
	}
	
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
}
