package com.ecquaria.controller;

import com.ecquaria.helper.JsonHelper;
import com.ecquaria.service.ResourceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ResourceClientService resourceClientService;
    @Value("${client.client-id}")
    private String clientId;
    @Value("${client.client-secret}")
    private String secret;
    @Value("${client.username}")
    private String username;
    @Value("${client.password}")
    private String password;

    @RequestMapping(value = "/test/**",method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        String token=getToken();
        String path=request.getRequestURI().replace("/test/","");
        return resourceClientService.queryFromResourceServer(path,token);
    }

    private String getToken(){

        String token=getTokenFromRedis();
        if(token==null){
            token=getTokenFromAuthorizationServer();
            //token=getTokenFromAuthorizationServerWithPasswordGrantType();
        }
        return token;
    }

    private String getTokenFromAuthorizationServer(){
        LinkedMultiValueMap parameters=new LinkedMultiValueMap();
        parameters.add("grant_type","client_credentials");
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization","Basic "+Base64.getEncoder().encodeToString((clientId+":"+secret).getBytes()));
        HttpEntity httpEntity=new HttpEntity(parameters,httpHeaders);
        String result=restTemplate.postForObject("http://zuul-server/authorization/oauth/token",httpEntity,String.class);
        System.out.println(result);
        Map map= JsonHelper.fromJson(result,Map.class);
        String token=(String)map.get("access_token");
        saveToRedis(token,new Long((int)map.get("expires_in")));
        return token;
    }

    private String getTokenFromAuthorizationServerWithPasswordGrantType(){
        LinkedMultiValueMap parameters=new LinkedMultiValueMap();
        parameters.add("grant_type","password");
        parameters.add("username",username);
        parameters.add("password",password);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Authorization","Basic "+ Base64.getEncoder().encodeToString((clientId+":"+secret).getBytes()));
        HttpEntity httpEntity=new HttpEntity(parameters,httpHeaders);
        String result=restTemplate.postForObject("http://zuul-server/authorization/oauth/token",httpEntity,String.class);
        System.out.println(result);
        Map map= JsonHelper.fromJson(result,Map.class);
        String token=(String)map.get("access_token");
        saveToRedis(token,new Long((int)map.get("expires_in")));
        return token;
    }

    private void saveToRedis(String token,long expires){
        //过期时间大于3分钟的token才保存到redis中
        if(expires>=180){
            expires=expires-60;//减去1分钟的时间，作为保存在redis中的过期时间
            redisTemplate.opsForValue().set("oauth_token_"+clientId,token,expires,TimeUnit.SECONDS);
        }
    }

    private String getTokenFromRedis(){
        return (String)redisTemplate.opsForValue().get("oauth_token_"+clientId);
    }

}
