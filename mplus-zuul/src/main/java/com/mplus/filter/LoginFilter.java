package com.mplus.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}


	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response =ctx.getResponse();
		try {
			//请求的uri 
			String uri = request.getRequestURI(); 
			log.info("===enter login filter: " + uri); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
