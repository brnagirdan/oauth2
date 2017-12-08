package com.ecquaria.oauth2.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zuul-server")
public interface PrivilegeValidFeign {

    @RequestMapping(value = "/rbac/aaa",method = RequestMethod.GET)
    ResponseEntity<Boolean> hasPrivilege(@RequestHeader HttpHeaders header);

}
