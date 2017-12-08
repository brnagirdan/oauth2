package com.ecquaria.oauth2.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "zuul-server")
public interface TokenFeign {

    @RequestMapping(value = "/authorization/oauth/check_token", method = RequestMethod.POST)
    Map<String, Object> getTokenInfo(@RequestHeader HttpHeaders header, @RequestParam("token") String value);

    @RequestMapping(value = "/authorization/oauth/token", method = RequestMethod.POST)
    ResponseEntity<OAuth2AccessToken> getAccessToken(@RequestHeader HttpHeaders header, @RequestParam Map<String, String> parameters);
}
