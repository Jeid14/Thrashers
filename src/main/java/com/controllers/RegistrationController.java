package com.controllers;

import com.model.User;
import com.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping(value = "/registration")
    public String registered(@Valid @RequestBody User user) {
        System.out.println(user);
        userRepo.save(user);
        return "success";
    }
}
