package com.mplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mplus.feign.TestFeign;

@RestController
public class HelloController {

	@Autowired
	TestFeign testFeign;
	
	@GetMapping(value = "/hello/{name}")
	public String hello(@PathVariable("name") String name) {
		return String.format("response from mplus-common: hello, %s", name);
	}
	
	@GetMapping(value = "/test")
	public String test(@RequestParam("name") String name) {
		return testFeign.test(name);
	}
}
