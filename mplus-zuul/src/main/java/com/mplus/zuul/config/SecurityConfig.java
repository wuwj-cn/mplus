package com.mplus.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Order(2)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
     * 1. 这里记得设置requestMatchers,不拦截需要token验证的url
     * 不然会优先被这个filter拦截,走用户端的认证而不是token认证
     * 2. 这里记得对oauth的url进行保护,正常是需要登录态才可以
     */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
			.requestMatchers().antMatchers("/authorize", "/oauth/**", "/auth/**")
			.and()
        	.authorizeRequests()
        	.anyRequest().permitAll();
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/css/**", "/js/**", "/favicon.ico");
	}
	
	/**
	 * Spring Boot 2.x不生成默认用户需要提供AuthenticationManager, AuthenticationProvider 
	 * or UserDetailsService中的一个bean
     * 这里配置一个userDetailsService Bean不在生成默认security.user用户
     * @return
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
}
