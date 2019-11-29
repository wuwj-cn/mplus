package com.mplus.auth.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.mplus.auth.service.CustomeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private CustomeUserDetailsService userDetailService;

    @Bean
    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
    	return new JwtTokenStore(jwtAccessTokenConverter());
    }
    
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
    	JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
    		@Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    			final Map<String, Object> info = new HashMap<String, Object>();
    			// 客户端模式下没有做用户认证, 取不到用户信息
    			if(!authentication.getOAuth2Request().getGrantType().contentEquals("client_credentials")) {
    				String userName = authentication.getUserAuthentication().getName();
    				info.put("user_name", userName);
    			}
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
    	};
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mplus-jwt.jks"), "123456".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mplus-jwt"));
        return converter;
    }

    //声明安全约束，哪些允许访问，哪些不允许访问
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security
	        .allowFormAuthenticationForClients()
	        .tokenKeyAccess("permitAll()")
	        .checkTokenAccess("isAuthenticated()");
    }

    //配置 ClientDetailsService 独立client客户端的信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }
    
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    //配置AuthorizationServer 端点的非安全属性，也就是 token 存储方式、token 配置、用户授权模式等
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints
    				.tokenStore(tokenStore())
    				// 不配置会导致token无法刷新
    				.userDetailsService(userDetailService)
    				.tokenEnhancer(jwtAccessTokenConverter())
    				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
    				.authenticationManager(authenticationManager);
//    	endpoints.tokenStore(tokenStore())
//		        .userDetailsService(userDetailService)
//		        .authenticationManager(authenticationManager);
		endpoints.tokenServices(defaultTokenServices());
		// 替换授权页面
//        endpoints.pathMapping("/oauth/confirm_access","/v1/auth/confirm_access");
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds((int)TimeUnit.HOURS.toSeconds(12));
        // refresh_token默认30天
        tokenServices.setRefreshTokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30));
        return tokenServices;
    }
}
