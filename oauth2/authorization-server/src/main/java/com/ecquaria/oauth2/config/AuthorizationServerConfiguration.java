package com.ecquaria.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("resource_client")
                .secret("password$1")
                .scopes("scope1")
                .authorizedGrantTypes("client_credentials")
                .and()
                .withClient("client_1")
                .secret("123456")
                .scopes("scope1")
                .authorizedGrantTypes("client_credentials","refresh_token")
                .accessTokenValiditySeconds(400)
                .refreshTokenValiditySeconds(600)
                .and()
                .withClient("client_2")
                .secret("123456")
                .scopes("scope0","scope2")
                .authorities("ROLE_B","ROLE_C")
                .authorizedGrantTypes("password","client_credentials","refresh_token")
                .accessTokenValiditySeconds(300)
                .refreshTokenValiditySeconds(500);
    }

    //声明授权和token的端点以及token的服务的一些配置信息，比如采用什么存储方式、token的有效期等
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager);
    }

    //声明安全约束，哪些允许访问，哪些不允许访问
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
