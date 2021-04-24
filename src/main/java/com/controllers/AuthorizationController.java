package com.controllers;

import com.model.Person;
import com.model.dto.PersonDTO;
import com.security.jwt.JwtTokenProvider;
import com.servises.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonService personService;

    public AuthorizationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PersonService personService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.personService = personService;
    }

    @PostMapping(value = "/authorization")
    public ResponseEntity<Object> auth(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
//        try {
        System.out.println("in start");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        String login = personDTO.getLogin();
        /** Вот с этим надо болие детально разобраться, когда делал - пиздил с видоса https://www.youtube.com/watch?v=yRnSUDx3Y8k .
         * По логике authenticationManager должен проверят валидность токена, но с этим какие то проблемы (у меня ща голова лопнет) отвечает 403 им кодом
         * Я забил и сделал костылями как это было.
         * На данном этапе контроллер отвечает в случае успеха как то так:
         * {
         *     "login": "vlad",
         *     "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2bGFkIiwiaWF0IjoxNjE5MjY2MTIxLCJleHAiOjE2MTkzMDkzMjF9.Dcg2VETpucwdzFNGBflFEbt_Rm5JuYyzQmod-FMHYpE"
         * }
         *
         * В принципе, и это может работать так как нам надо, но я не уверен. (+ хотелось бы разобраться с этим менеджером)
         **/
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login, personDTO.getPassword());
//            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Person person = personService.findByLogin(login);
        if (person == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (!personService.getPasswordEncoder().matches(personDTO.getPassword(), person.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }
        String token = jwtTokenProvider.createToken(login);
        System.out.println(token);

        Map<Object, Object> response = new HashMap<>();
        response.put("login", login);
        response.put("token", token);
        System.out.println("end");
        return ResponseEntity.ok(response);
//        } catch (AuthenticationException | IllegalArgumentException e) {
//            throw new BadCredentialsException("illegal pass or login");
//        }
    }
}
