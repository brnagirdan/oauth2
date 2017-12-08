package com.ecquaria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PrivilegeController {

    @RequestMapping(value = "/aaa",method = RequestMethod.GET)
    public ResponseEntity<Boolean> hasPrivilege(HttpServletRequest request){

        //String url=request.getParameter("url");
        //String username=request.getParameter("username");

        return new ResponseEntity(true,HttpStatus.OK);
    }

}
