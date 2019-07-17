package com.brt.bref.auth.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.brt.bref.auth.security.BrefUserAuthenticationConverter;
import com.brt.bref.auth.security.constant.SecurityConstant;
import com.brt.bref.auth.security.service.BrefClientDetailsService;
import com.brt.bref.auth.security.service.JdbcUserDetailsService;
import com.brt.bref.auth.security.service.impl.JdbcUserDetailsServiceImpl;

import org.springframework.http.HttpMethod;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("clientDataSource")
	private DataSource dataSource;
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private JdbcUserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		BrefClientDetailsService clientDetailsService = new BrefClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityConstant.DEFAULT_CLIENT_SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityConstant.DEFAULT_CLIENT_FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.allowFormAuthenticationForClients()
			.checkTokenAccess("permitAll()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		DefaultAccessTokenConverter defaultAccessTokenConverter=new DefaultAccessTokenConverter();
		defaultAccessTokenConverter.setUserTokenConverter(new BrefUserAuthenticationConverter());
		endpoints
			.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancer())
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager)
			.accessTokenConverter(defaultAccessTokenConverter)
			.reuseRefreshTokens(false);
	}
	
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityConstant.BREF_PREFIX + SecurityConstant.OAUTH_PREFIX);
		return tokenStore;
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			final Map<String, Object> additionalInfo = new HashMap<>(1);		
			additionalInfo.put("copyright", SecurityConstant.BREF_COPYRIGHT);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
	
	@Bean(name = "jdbcUserDetailsService")
    public JdbcUserDetailsService jdbcUserDetailsServiceBin(@Qualifier("userDataSource") DataSource dataSource) {
        return new JdbcUserDetailsServiceImpl(dataSource);
    }
}
