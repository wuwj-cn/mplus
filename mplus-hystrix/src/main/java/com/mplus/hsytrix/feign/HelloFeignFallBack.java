package com.mplus.hsytrix.feign;

import org.springframework.stereotype.Component;

@Component
public class HelloFeignFallBack implements HelloFeign {

	@Override
	public String hello(String name) {
		return "error: hello feign fallback...";
	}

}
