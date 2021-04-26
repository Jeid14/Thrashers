package com.controllers;

import com.model.Person;
import com.model.dto.PersonDTO;
import com.security.jwt.JwtTokenProvider;
import com.servises.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.util.Fields.LOGIN;
import static com.util.Fields.TOKEN;
import static com.util.Mappings.AUTHORIZATION;


@RestController
@CrossOrigin("http://localhost:4200")
public class AuthorizationController {
    private static final String USER_NOT_FOUND = "User not found";
    private static final String INCORRECT_PASSWORD = "Incorrect password";

    private final JwtTokenProvider jwtTokenProvider;
    private final PersonService personService;

    public AuthorizationController(JwtTokenProvider jwtTokenProvider, PersonService personService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.personService = personService;
    }

    @PostMapping(value = AUTHORIZATION)
    public ResponseEntity<Object> auth(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String login = personDTO.getLogin();

        Person person = personService.findByLogin(login);
        if (person == null) {
            return new ResponseEntity<>(USER_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }

        if (!personService.getPasswordEncoder().matches(personDTO.getPassword(), person.getPassword())) {
            return new ResponseEntity<>(INCORRECT_PASSWORD, HttpStatus.UNAUTHORIZED);
        }
        String token = jwtTokenProvider.createToken(login);
        System.out.println(token);

        Map<Object, Object> response = new HashMap<>();
        response.put(LOGIN, login);
        response.put(TOKEN, token);

        return ResponseEntity.ok(response);
    }
}
