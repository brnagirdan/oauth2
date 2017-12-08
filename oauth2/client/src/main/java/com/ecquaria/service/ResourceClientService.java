package com.ecquaria.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceClientService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String queryFromResourceServer(String path,String token){
        System.out.println("token:"+token);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+token);
        HttpEntity httpEntity=new HttpEntity(null,httpHeaders);
        return restTemplate.exchange("http://resource-server/resource/"+path,HttpMethod.GET,httpEntity,String.class).getBody();
    }

    public String error(String path,String token){
        return "ERROR! MAYBE YOU DO NOT HAVE PRIVILEGE.";
    }
}
