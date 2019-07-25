package com.mplus.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mplus-hystrix",fallback = TestFeignFallBack.class)
public interface TestFeign {

	@GetMapping(value = "/test")
	public String test(@RequestParam("name") String name);
}
