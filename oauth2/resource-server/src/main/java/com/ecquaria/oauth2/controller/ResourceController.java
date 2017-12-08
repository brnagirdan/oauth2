package com.ecquaria.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ResourceController {

    @RequestMapping(value="/resource/**",method = RequestMethod.GET)
    public String getResource(){
        return "resource_"+ UUID.randomUUID();
    }

    @RequestMapping(value="/resource1",method = RequestMethod.GET)
    public String getResource1(){
        return "resource1_"+UUID.randomUUID();
    }

}
