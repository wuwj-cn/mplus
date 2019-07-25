package com.mplus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.mplus.handler.MyAuthenticationFailureHandler;
import com.mplus.handler.MyAuthenticationSuccessHandler;
import com.mplus.handler.MyLogoutSuccessHandler;
import com.mplus.service.CustomeAuthenticationProvider;
import com.mplus.service.CustomePasswordEncoder;
import com.mplus.service.CustomeUserDetailsService;

@Order(1)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${mplus.auth.loginPage}")
	private String loginPage;

	@Value("${mplus.auth.loginProcessingUrl}")
	private String loginProcessingUrl;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new CustomePasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			// 不能禁用session, 否则将导致认证后无法跳转到认证前的页面
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
			// 必须配置，不然OAuth2的http配置不生效
			.requestMatchers().antMatchers("/oauth/authorize", loginProcessingUrl) 
	        .and()
	        .authorizeRequests()
//	        .antMatchers("/v1/auth/**").permitAll()
	        .anyRequest().authenticated()
	        .and().formLogin()
	        .loginPage(loginPage)
	        .loginProcessingUrl(loginProcessingUrl).permitAll()
//	        .and().httpBasic()
	        //登录成功处理
	        .successHandler(myAuthenticationSuccessHandler())
	        //登录失败处理
	        .failureHandler(myAuthenticationFailureHandler())
	        .and().logout()
	        .logoutSuccessHandler(myLogoutSuccessHandler())
	        ;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/", "/css/**", "/js/**", "/plugins/**", "/favicon.ico");
	}
	
	@Bean
	public CustomeUserDetailsService userDetailsService() {
		return new CustomeUserDetailsService();
	}
	
	@Bean
    public CustomeAuthenticationProvider customeAuthenticationProvider() {
		CustomeAuthenticationProvider provider = new CustomeAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(customeAuthenticationProvider());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    /**
     * 注册  登录成功 处理 bean
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MyAuthenticationSuccessHandler();
    }

    /**
     *  注册 登录失败 处理 bean
     * @return
     */
    @Bean
    public AuthenticationFailureHandler myAuthenticationFailureHandler(){
        return new MyAuthenticationFailureHandler();
    }
    
    /**
     * 注册 退出系统成功 处理bean
     * @return
     */
    @Bean
    public LogoutSuccessHandler myLogoutSuccessHandler(){
        return new MyLogoutSuccessHandler();
    }
}
