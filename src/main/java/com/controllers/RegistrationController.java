package com.controllers;

import com.model.dto.PersonDTO;
import com.model.User;
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
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public RegistrationController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<Object> registered(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(bindingResult.getAllErrors().get(0).getDefaultMessage());

        }
        User user = User.builder()
                .login(personDTO.getLogin())
                .password(passwordEncoder.encode(personDTO.getPassword()))
                .build();
        if (userRepo.findUsersByLogin(user.getLogin()) != null) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body("User exist");
        }
        userRepo.save(user);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("success");
    }


}
