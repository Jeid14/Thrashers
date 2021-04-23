package com.controllers;

import com.model.User;
import com.model.dto.PersonDTO;
import com.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthorizationControler {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/auth")
    public ResponseEntity<Object> auth(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        System.out.println("auth");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
//        User user = User.builder()
//                .login(personDTO.getLogin())
//                .password(passwordEncoder.encode(personDTO.getPassword()))
//                .build();
        User inDb = userRepo.findUsersByLogin(personDTO.getLogin());
        if (inDb == null) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("User not found");
        }
        if (passwordEncoder.matches(personDTO.getPassword(), inDb.getPassword())) {
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("success");
        }
        return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("Неверный пароль!");
    }
}
