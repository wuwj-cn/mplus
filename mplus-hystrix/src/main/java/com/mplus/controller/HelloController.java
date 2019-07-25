package com.mplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.feign.HelloFeign;

@RestController
public class HelloController {

	@Autowired
	private HelloFeign helloFeign;
	
	@GetMapping("/hello")
	public String hello(@RequestParam("name") String name) {
		return helloFeign.hello(name);
	}
	
	@GetMapping("/test")
	public String test(@RequestParam("name") String name) {
		return String.format("response from mplus-hystrix: %s", name);
	}
}
