package com.ecquaria.oauth2.config;

import com.ecquaria.oauth2.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().disable()//不允许匿名访问，默认情况下，匿名用户有一个AnonymousAuthenticationToken标示，包含角色"ROLE_ANONYMOUS"
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//让 Spring Security 不创建和使用 session
                .and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().anyRequest().authenticated();
    }

    // 不需要权限控制的路径
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/info");
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中保存两个用户
        auth.inMemoryAuthentication().withUser("egovadmin:sys1").password("b13f23d44c980e8ce8ed6e820544398c830049fa5b6f82defe4f04e1df865626").authorities("ROLE_RE","ROLE_XS")
            .and().withUser("admin").password("admin123").authorities("ROLE_ADMIN","ROLE_A");
    }*/

    @Autowired
    private MyUserDetailsService userDetailsService;

    //自定义userDetailsService，来从数据库或其他地方得到用户和对应的role
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //不定义没有password grant_type (密码模式，oauth2的4种模式之一)
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
