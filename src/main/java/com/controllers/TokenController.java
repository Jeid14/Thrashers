package com.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {
    @CrossOrigin("http://localhost:4200")
    @PostMapping("/token")
    public String chekToken(HttpServletRequest request){
        return "work";
    }
}

