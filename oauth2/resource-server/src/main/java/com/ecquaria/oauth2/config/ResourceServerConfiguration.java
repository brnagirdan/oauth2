package com.ecquaria.oauth2.config;


import com.ecquaria.oauth2.feign.TokenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /*@Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                //.antMatchers("/resource*//**").authenticated()//需要认证的请求
                .antMatchers("/resource1*//**").permitAll()//不需要认证的请求
                //.anyRequest().authenticated()//除了已配置的无需认证的请求之外的请其他求都需要认证
                .anyRequest()*//*.access("#oauth2.hasScope('scope1')")*//*
                .authenticated()
                //.access("hasRole('ROLE_A')")
                .and()
                .httpBasic();
    }*/

    //注册表达式解析器，否则无法识别下方的 @mySecurity.check(authentication,request)
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    //自定义token service 用于check token
    @Primary
    @Bean
    public ResourceServerTokenServices tokenServices(
            @Autowired ResourceServerProperties resource,@Autowired TokenFeign tokenFeign){
        MyTokenServices services=new MyTokenServices();
        services.setClientId(resource.getClientId());
        services.setClientSecret(resource.getClientSecret());
        services.setTokenFeign(tokenFeign);
        return services;
    }

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(expressionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/resource1").permitAll()
                .anyRequest().access("@mySecurity.check(#oauth2,authentication,request)")//access里的方法返回true则允许访问
                .and()
                .httpBasic();
    }

}
