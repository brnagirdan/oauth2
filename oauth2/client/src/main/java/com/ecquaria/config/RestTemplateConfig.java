package com.ecquaria.config;

import com.ecquaria.interceptor.RestTemplateRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        builder.setReadTimeout(35000);
        builder.setConnectTimeout(20000);
        RestTemplate restTemplate=builder.build();
        List<ClientHttpRequestInterceptor> interceptors=new ArrayList<>();
        interceptors.add(new RestTemplateRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

}