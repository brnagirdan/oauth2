package com.ecquaria.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "zuul-server")
public interface TokenFeign {

    @RequestMapping(value = "/authorization/oauth/check_token", method = RequestMethod.POST)
    Map<String, Object> getTokenInfo(@RequestHeader HttpHeaders header, @RequestParam("token") String value);

}
