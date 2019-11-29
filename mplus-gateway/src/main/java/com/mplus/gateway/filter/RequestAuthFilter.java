package com.mplus.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestAuthFilter implements Ordered, GlobalFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest serverHttpRequest = exchange.getRequest();
		String method = serverHttpRequest.getMethodValue();
//		if(!"POST".equals(method)){
//			ServerHttpResponse response = exchange.getResponse();
//			String message= "非法请求";
//			byte[] bits = message.getBytes(StandardCharsets.UTF_8);
//			DataBuffer buffer = response.bufferFactory().wrap(bits);
//			response.setStatusCode(HttpStatus.UNAUTHORIZED);
//			//指定编码，否则在浏览器中会中文乱码
//			response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
//			return response.writeWith(Mono.just(buffer));
//		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
