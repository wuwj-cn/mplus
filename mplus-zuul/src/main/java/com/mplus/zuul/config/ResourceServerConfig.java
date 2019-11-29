package com.mplus.zuul.config;

import com.mplus.zuul.handler.MyAccessDeniedHandler;
import com.mplus.zuul.handler.MyAuthenticationEntryPointHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;


@Configuration
@EnableResourceServer
@Order(10)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "mplus-zuul";
	
	@Autowired
    private TokenStore tokenStore;
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                // 请求资源鉴权失败
                .authenticationEntryPoint(myAuthenticationEntryPoint())
                // 请求资源权限不足
                .accessDeniedHandler(myAccessDeniedHandler())
                .and()
                .authorizeRequests()
            	.antMatchers("/auth/**", "/actuator/**").permitAll()
            	.anyRequest().authenticated();
    }
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
        resources.resourceId(RESOURCE_ID).stateless(true);
        resources.tokenServices(tokenServices());
    }
	
	@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);
        return defaultTokenServices;
    }
	
	/**
     * 请求资源认证失败
     * @return
     */
    @Bean
    public AuthenticationEntryPoint myAuthenticationEntryPoint(){
        return new MyAuthenticationEntryPointHandler();
    }

    /**
     * 请求资源认证权限不足
     * @return
     */
    @Bean
    public AccessDeniedHandler myAccessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }
}
