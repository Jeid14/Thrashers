package com.controllers;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.repo.UserRepo;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/reg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registered(@Valid @RequestBody User user) {
        System.out.println(user);
        userRepo.save(user);
        return "success";
    }
}
