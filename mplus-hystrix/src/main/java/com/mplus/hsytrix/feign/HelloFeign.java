package com.mplus.hsytrix.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "mplus-common",fallback = HelloFeignFallBack.class)
public interface HelloFeign {

	@GetMapping(value = "/hello/{name}")
	public String hello(@PathVariable("name") String name);
}
