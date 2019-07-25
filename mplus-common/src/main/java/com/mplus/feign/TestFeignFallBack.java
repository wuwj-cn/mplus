package com.mplus.feign;

import org.springframework.stereotype.Component;

@Component
public class TestFeignFallBack implements TestFeign {

	@Override
	public String test(String name) {
		return "error: test feign fallback...";
	}

}
