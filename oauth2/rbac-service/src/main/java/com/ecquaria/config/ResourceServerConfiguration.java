package com.ecquaria.config;


import com.ecquaria.feign.TokenFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    //自定义token service 用于check token
    @Primary
    @Bean
    public ResourceServerTokenServices tokenServices(
            @Autowired ResourceServerProperties resource, @Autowired TokenFeign tokenFeign){
        MyTokenServices services=new MyTokenServices();
        services.setClientId(resource.getClientId());
        services.setClientSecret(resource.getClientSecret());
        services.setTokenFeign(tokenFeign);
        return services;
    }

}
