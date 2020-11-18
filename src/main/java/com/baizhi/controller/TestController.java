package com.baizhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/test/test")
    public String test(String usernmae, HttpServletRequest request){
        request.getSession().setAttribute("usernmae", usernmae);
        return "login ok";
    }
}
